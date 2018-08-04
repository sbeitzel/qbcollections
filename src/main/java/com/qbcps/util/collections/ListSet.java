package com.qbcps.util.collections;
/*
 * File copyright 8/14/13 by Stephen Beitzel
 */

import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Collection that imposes the uniqueness constraint of the Set
 * interface and offers the ordering and stable traversal properties
 * of a List.
 * <br>
 * <em>Implementation note: because of the Set guarantees, the
 * {@link java.util.Collections#sort(List)} methods will not work properly. If
 * you need to sort a <tt>ListSet</tt> you should export it to an array, sort
 * the array, then add the sorted elements to a fresh <tt>ListSet</tt>.</em>
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
public class ListSet<T> implements List<T>, Set<T> {
    List<T> _list;
    private Set<T> _set;

    ListSet() {
        _set = new HashSet<>();
    }

    @Override
    public int size() {
        return _list.size();
    }

    @Override
    public boolean isEmpty() {
        return _list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return _set.contains(o);
    }

    @Override
    public Object[] toArray() {
        return _list.toArray();
    }

    @SuppressWarnings("SuspiciousToArrayCall")
    @Override
    public <O> O[] toArray(O[] t1s) {
        return _list.toArray(t1s);
    }

    @Override
    public boolean add(T t) {
        return _set.add(t) && _list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        boolean result = _set.remove(o);
        result |= _list.remove(o);
        return result;
    }

    @Override
    public boolean containsAll(Collection<?> objects) {
        return _set.containsAll(objects) && _list.containsAll(objects);
    }

    @Override
    public boolean addAll(Collection<? extends T> ts) {
        boolean result = false;
        for (T element : ts) {
            result |= add(element);
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> objects) {
        boolean result = false;
        for (Object o : objects) {
            result |= remove(o);
        }
        return result;
    }

    @Override
    public void clear() {
        _set.removeAll(_list);
        _list.clear();
    }

    @Override
    public T get(int i) {
        return _list.get(i);
    }

    @Override
    public void add(int i, T t) {
        if (t == null) {
            throw new NullPointerException("Null elements not allowed");
        }
        if (!contains(t)) {
            // the order here is important. The list might throw an ArrayIndexOutOfBoundsException.
            _list.add(i, t);
            _set.add(t);
        }
    }

    @Override
    public T remove(int i) {
        T thing = _list.remove(i);
        _set.remove(thing);
        return thing;
    }

    @Override
    public int indexOf(Object o) {
        return _list.indexOf(o);
    }

    @Override
    public T set(int i, T t) {
        if (!contains(t)) {
            _set.add(t);
            T oldT = _list.set(i, t);
            _set.remove(oldT);
            return oldT;
        }
        throw new IllegalArgumentException("attempt to insert duplicate element");
    }

    @Override
    public int lastIndexOf(Object o) {
        return _list.lastIndexOf(o);
    }

    @Override
    public List<T> subList(int i, int i2) {
        List<T> sll = _list.subList(i, i2);
        ListSet<T> sublist = new ListSet<>();
        sublist._set = _set;
        sublist._list = sll;
        return sublist;
    }

    @Override
    public Spliterator<T> spliterator() {
        return _list.spliterator();
    }

    @Override
    public Stream<T> stream() {
        return _list.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return _list.parallelStream();
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        _list.forEach(action);
    }

    @Override
    public boolean addAll(int i, Collection<? extends T> ts) {
        ListItr iter = new ListItr(i);
        int size = _list.size();
        for (T thing : ts) {
            iter.add(thing);
        }
        return size != _list.size();
    }

    @Override
    public boolean retainAll(Collection<?> objects) {
        boolean modified = false;
        // we're gonna use the list iterator to do this
        ListItr myIter = new ListItr(0);
        while (myIter.hasNext()) {
            Object thing = myIter.next();
            if (!objects.contains(thing)) {
                myIter.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sort(Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        if (i < 0 || i > _list.size()-1) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return new ListItr(i);
    }

    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<T> {
        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor < _list.size();
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            int i = cursor;
            if (i >= _list.size())
                throw new NoSuchElementException();
            cursor = i + 1;
            return ListSet.this.get(lastRet = i);
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                ListSet.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * An optimized version of AbstractList.ListItr
     */
    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            if (i >= _list.size())
                throw new ConcurrentModificationException();
            cursor = i;
            return ListSet.this.get(lastRet = i);
        }

        @Override
        public void set(T e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                ListSet.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(T e) {
            // only update the iterator's state if the element will really be added
            try {
                if (!ListSet.this.contains(e)) {
                    int i = cursor;
                    ListSet.this.add(i, e);
                    cursor = i + 1;
                    lastRet = -1;
                }
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

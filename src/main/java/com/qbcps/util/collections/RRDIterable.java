package com.qbcps.util.collections;
/*
 * Copyright 8/3/18 by Stephen Beitzel
 */

import java.util.Iterator;

/**
 * This is a fixed-size collection that allows appending but not
 * deletion. For a collection of elements indexed 0 to N, adding the N+1th element
 * will result in a collection with elements 1 to N+1, with
 * the N+1 element overwriting the 0th element. This behavior is the
 * same as the data storage for <a href="https://oss.oetiker.ch/rrdtool/" target="_blank">RRDTool</a>.
 * <br/><br/>
 * This class is <em>NOT</em> thread safe. Clients requiring thread safety
 * should synchronize on the instance or on some other external lock.
 * <br/><br/>
 * Implementation note: this implementation is backed by an array.
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
public class RRDIterable<T> implements Iterable<T> {

    private T[] _elements;
    private int _insertionPoint = 0;

    @SuppressWarnings("unchecked")
    public RRDIterable(int size) {
        _elements = (T[]) new Object[size];
        for (int i = 0; i<size; i++) {
            _elements[i] = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator iter = iterator();
        while (iter.hasNext()) {
            sb.append(iter.next());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new RRDIterator();
    }

    /**
     * Add an element to the collection, evicting the oldest element if necessary.
     * Nulls are allowed.
     *
     * @param element the element to add
     */
    public void add(T element) {
        _elements[_insertionPoint++] = element;
        if (_insertionPoint >= _elements.length) {
            _insertionPoint = 0;
        }
    }

    public int size() {
        return _elements.length;
    }

    private class RRDIterator implements Iterator<T> {
        private int _index;
        private int _startingPoint;
        private boolean _started = false;

        RRDIterator() {
            _index = _insertionPoint-1;
            if (_index < 0) {
                _index = _elements.length -1;
            }
            _startingPoint = _index;
        }

        @Override
        public boolean hasNext() {
            return !_started || _index != _startingPoint;
        }

        @Override
        public T next() {
            _started = true;
            T element = _elements[_index--];
            if (_index <0) {
                _index = _elements.length-1;
            }
            return element;
        }
    }
}

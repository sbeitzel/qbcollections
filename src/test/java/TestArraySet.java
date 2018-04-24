import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.qbcps.util.collections.ArraySet;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for ArraySet
 *
 * File copyright 8/14/13 by Stephen Beitzel
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
@Test(groups = {"all", "smoke", "collections"})
public class TestArraySet {
    @Test
    public void testAdd() {
        // you can add an item to an ArraySet and the item is there
        ArraySet<Integer> as = new ArraySet<>();
        Integer five = Integer.valueOf(5);
        as.add(five);
        Assert.assertEquals(as.size(), 1);
        Assert.assertTrue(as.contains(five));
    }

    @Test
    public void testAddCollection() {
        LinkedList<Integer> source = new LinkedList<>();
        for (int i = 0; i<10; i++) {
            source.add(Integer.valueOf(i));
        }
        ArraySet<Integer> dest = new ArraySet<>(source);
        Assert.assertEquals(dest.size(), source.size());
        for (Integer integer : source) {
            Assert.assertTrue(dest.contains(integer));
            Assert.assertEquals(dest.indexOf(integer), source.indexOf(integer));
        }
    }

    @Test
    public void testDupes() {
        ArraySet<Integer> as = new ArraySet<>();
        Integer five = Integer.valueOf(5);
        Assert.assertTrue(as.add(five));
        Assert.assertFalse(as.add(five), "Duplicate add should have returned false");
        Assert.assertEquals(as.size(), 1);
        Assert.assertTrue(as.contains(five));
    }

    @Test
    public void testOrder() {
        ArraySet<Integer> as = new ArraySet<>();
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
        }
        Assert.assertEquals(as.size(), 500, "collection doesn't contain the right number of elements");
        for (int i = 1; i<500; i++) {
            Integer prev = as.get(i-1);
            Integer curr = as.get(i);
            Assert.assertNotNull(prev);
            Assert.assertNotNull(curr);
            Assert.assertTrue(prev.intValue() < curr.intValue(), "Order is not stable!");
        }
    }

    @Test
    public void testContains() {
        ArraySet<Integer> as = new ArraySet<>();
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
        }
        Assert.assertEquals(as.size(), 500, "collection doesn't contain the right number of elements");
        for (int i = 0; i<500; i++) {
            Assert.assertTrue(as.contains(Integer.valueOf(i)));
        }
    }

    @Test
    public void testRemove() {
        ArraySet<Integer> as = new ArraySet<>();
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
        }
        Assert.assertEquals(as.size(), 500, "collection doesn't contain the right number of elements");
        for (int i = 0; i<500; i++) {
            Assert.assertTrue(as.contains(Integer.valueOf(i)));
        }
        as.remove(Integer.valueOf(50));
        Assert.assertFalse(as.contains(Integer.valueOf(50)));
    }

    @Test
    public void testContainsAll() {
        ArraySet<Integer> as = new ArraySet<>();
        ArrayList<Integer> al = new ArrayList<>(500);
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
            al.add(Integer.valueOf(i));
        }
        Assert.assertTrue(as.containsAll(al));
        al.add(Integer.valueOf(600));
        Assert.assertFalse(as.containsAll(al));
    }

    @Test
    public void testSubList() {
        ArraySet<Integer> as = new ArraySet<>();
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
        }
        List<Integer> sl = as.subList(99, 199);
        sl.clear();
        Assert.assertFalse(as.isEmpty(), "Clear of sublist emptied the list");
        Assert.assertTrue(sl.isEmpty(), "Clear of sublist didn't empty the sublist");
        for (int i=99; i<199; i++) {
            Assert.assertFalse(as.contains(Integer.valueOf(i)), "Oh noes! After clear the list still contains "+i);
        }
        Assert.assertEquals(as.size(), 400);
    }

    @Test
    public void testClear() {
        ArraySet<Integer> as = new ArraySet<>();
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
        }
        as.clear();
        Assert.assertTrue(as.isEmpty());
        Object [] myRA = as.toArray();
        Assert.assertEquals(myRA.length, 0);
    }

    @Test
    public void testToArray() {
        ArraySet<Integer> as = new ArraySet<>();
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
        }
        Integer [] myRA = as.toArray(new Integer[500] );
        Assert.assertEquals(myRA.length, 500);
        for (int i=0; i<myRA.length; i++) {
            Assert.assertEquals(myRA[i].intValue(), i);
        }
    }

    @Test
    public void testRetainAll() {
        ArraySet<Integer> as = new ArraySet<>();
        as.add(Integer.valueOf(1));
        as.add(Integer.valueOf(2));
        as.add(Integer.valueOf(3));
        as.add(Integer.valueOf(4));

        Set<Integer> odds = new HashSet<>();
        odds.add(Integer.valueOf(1));
        odds.add(Integer.valueOf(3));

        as.retainAll(odds);

        Assert.assertEquals(as.size(), odds.size());
        Assert.assertFalse(as.contains(Integer.valueOf(2)));
        Assert.assertTrue(odds.containsAll(as));
    }

    @Test
    public void testIterator() {
        ArraySet<Integer> as = new ArraySet<>();
        for (int i = 0; i<500; i++) {
            as.add(Integer.valueOf(i));
        }
        Iterator<Integer> it = as.iterator();
        Assert.assertNotNull(it, "Iterator is null, FFS!");
        Integer in = it.next();
        Assert.assertEquals(in, Integer.valueOf(0));
    }

    @Test
    public void testGet() {
        ArraySet<Integer> as = new ArraySet<>();
        as.add(Integer.valueOf(5));
        as.add(Integer.valueOf(6));
        Assert.assertEquals(as.get(0), Integer.valueOf(5));
        Assert.assertEquals(as.get(1), Integer.valueOf(6));
    }

    public void testIndexOf() {
        ArraySet<Integer> as = new ArraySet<>();
        as.add(Integer.valueOf(5));
        as.add(Integer.valueOf(6));
        Assert.assertEquals(as.indexOf(Integer.valueOf(6)), 1);
    }
}


/*
 * Copyright 8/3/18 by Stephen Beitzel
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.qbcps.util.collections.ListSet;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
@Test(groups = {"all", "smoke", "collections"})
public class TestListSet {

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testAdd(String typeName, ListSet<Integer> theSet) {
        // you can add an item to a ListSet and the item is there
        Integer five = Integer.valueOf(5);
        theSet.add(five);
        Assert.assertEquals(theSet.size(), 1, typeName+" failed add");
        Assert.assertTrue(theSet.contains(five));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testRemoveAll(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        Collection<Integer> duds = Arrays.asList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2));
        Assert.assertTrue(theSet.removeAll(duds));
        Assert.assertFalse(theSet.containsAll(duds));
        Assert.assertEquals(theSet.size(), 497);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testAddAtIndex(String typeName, ListSet<Integer> theSet) {
        theSet.add(Integer.valueOf(5));
        theSet.add(Integer.valueOf(6));
        try {
            theSet.add(1, null);
            Assert.fail(typeName+" is allowing insertion of null element");
        } catch (NullPointerException npe) {
            // expected
        }
        theSet.add(1, Integer.valueOf(4));
        Assert.assertEquals(theSet.indexOf(Integer.valueOf(4)), 1);
        Assert.assertEquals(theSet.size(), 3);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testAddAllAtIndex(String typeName, ListSet<Integer> theSet) {
        theSet.add(Integer.valueOf(5));
        theSet.add(Integer.valueOf(6));
        Collection<Integer> newbs = Arrays.asList(Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2));
        Collection<Integer> bogus = Arrays.asList(Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5));
        try {
            theSet.addAll(3, newbs);
            Assert.fail(typeName+": able to attempt insert at invalid index");
        } catch (IndexOutOfBoundsException oob) {
            // expected
        }
        try {
            theSet.addAll(1, bogus);
            Assert.fail(typeName+": able to attempt adding duplicate element without exception");
        } catch (IllegalArgumentException iae) {
            // expected
        }
        theSet.addAll(1, newbs);
        Assert.assertEquals(theSet.size(), 5);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testReplaceAll(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        theSet.replaceAll(integer -> Integer.valueOf(integer.intValue() - 1));
        Assert.assertTrue(theSet.contains(Integer.valueOf(-1)));
        Assert.assertFalse(theSet.contains(Integer.valueOf(499)));
        Assert.assertEquals(theSet.size(), 500);
        try {
            theSet.replaceAll(integer -> Integer.valueOf(integer.intValue() + 1));
            Assert.fail(typeName+": allows insertion of duplicate element via replaceAll()");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testListIteratorPrevious(String typeName, ListSet<Integer> theSet) {
        for (int i=0; i<20; i++) {
            theSet.add(Integer.valueOf(i));
        }
        ListIterator<Integer> li = theSet.listIterator();
        Integer previous = li.next();
        Integer current = li.next();
        Assert.assertEquals(li.previous(), current);
        li.set(Integer.valueOf(-1));
        Assert.assertEquals(li.next(), Integer.valueOf(-1));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testSetValue(String typeName, ListSet<Integer> theSet) {
        theSet.add(Integer.valueOf(5));
        theSet.add(Integer.valueOf(6));
        try {
            theSet.set(1, Integer.valueOf(5));
            Assert.fail(typeName+": able to insert duplicate value");
        } catch (IllegalArgumentException iae) {
            // expected
        }
        theSet.set(1, Integer.valueOf(4));
        Assert.assertEquals(theSet.get(0), Integer.valueOf(5));
        Assert.assertEquals(theSet.get(1), Integer.valueOf(4));
        Assert.assertEquals(theSet.size(), 2);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testDupes(String typeName, ListSet<Integer> theSet) {
        Integer five = Integer.valueOf(5);
        Assert.assertTrue(theSet.add(five));
        Assert.assertFalse(theSet.add(five), typeName+": Duplicate add should have returned false");
        Assert.assertEquals(theSet.size(), 1);
        Assert.assertTrue(theSet.contains(five));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testOrder(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        Assert.assertEquals(theSet.size(), 500, typeName+ ": collection doesn't contain the right number of elements");
        for (int i = 1; i<500; i++) {
            Integer prev = theSet.get(i-1);
            Integer curr = theSet.get(i);
            Assert.assertNotNull(prev);
            Assert.assertNotNull(curr);
            Assert.assertTrue(prev.intValue() < curr.intValue(), "Order is not stable!");
        }
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testContains(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        Assert.assertEquals(theSet.size(), 500, typeName+": collection doesn't contain the right number of elements");
        for (int i = 0; i<500; i++) {
            Assert.assertTrue(theSet.contains(Integer.valueOf(i)));
        }
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testRemove(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        Assert.assertEquals(theSet.size(), 500, typeName+": collection doesn't contain the right number of elements");
        for (int i = 0; i<500; i++) {
            Assert.assertTrue(theSet.contains(Integer.valueOf(i)));
        }
        theSet.remove(Integer.valueOf(50));
        Assert.assertFalse(theSet.contains(Integer.valueOf(50)));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testContainsAll(String typeName, ListSet<Integer> theSet) {
        ArrayList<Integer> al = new ArrayList<>(500);
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
            al.add(Integer.valueOf(i));
        }
        Assert.assertTrue(theSet.containsAll(al));
        al.add(Integer.valueOf(600));
        Assert.assertFalse(theSet.containsAll(al));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testSubList(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        List<Integer> sl = theSet.subList(99, 199);
        sl.clear();
        Assert.assertFalse(theSet.isEmpty(), typeName+": Clear of sublist emptied the list");
        Assert.assertTrue(sl.isEmpty(), typeName+": Clear of sublist didn't empty the sublist");
        for (int i=99; i<199; i++) {
            Assert.assertFalse(theSet.contains(Integer.valueOf(i)), typeName+ ": Oh noes! After clear the list still contains "+i);
        }
        Assert.assertEquals(theSet.size(), 400);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testClear(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        theSet.clear();
        Assert.assertTrue(theSet.isEmpty());
        Object [] myRA = theSet.toArray();
        Assert.assertEquals(myRA.length, 0);
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testToArray(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        Integer [] myRA = theSet.toArray(new Integer[500] );
        Assert.assertEquals(myRA.length, 500);
        for (int i=0; i<myRA.length; i++) {
            Assert.assertEquals(myRA[i].intValue(), i);
        }
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testRetainAll(String typeName, ListSet<Integer> theSet) {
        theSet.add(Integer.valueOf(1));
        theSet.add(Integer.valueOf(2));
        theSet.add(Integer.valueOf(3));
        theSet.add(Integer.valueOf(4));

        Set<Integer> odds = new HashSet<>();
        odds.add(Integer.valueOf(1));
        odds.add(Integer.valueOf(3));

        theSet.retainAll(odds);

        Assert.assertEquals(theSet.size(), odds.size());
        Assert.assertFalse(theSet.contains(Integer.valueOf(2)));
        Assert.assertTrue(odds.containsAll(theSet));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testIterator(String typeName, ListSet<Integer> theSet) {
        for (int i = 0; i<500; i++) {
            theSet.add(Integer.valueOf(i));
        }
        Iterator<Integer> it = theSet.iterator();
        Assert.assertNotNull(it, typeName+": Iterator is null, FFS!");
        Integer in = it.next();
        Assert.assertEquals(in, Integer.valueOf(0));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testGet(String typeName, ListSet<Integer> theSet) {
        theSet.add(Integer.valueOf(5));
        theSet.add(Integer.valueOf(6));
        Assert.assertEquals(theSet.get(0), Integer.valueOf(5));
        Assert.assertEquals(theSet.get(1), Integer.valueOf(6));
    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "listSets")
    public void testIndexOf(String typeName, ListSet<Integer> theSet) {
        theSet.add(Integer.valueOf(5));
        theSet.add(Integer.valueOf(6));
        Assert.assertEquals(theSet.indexOf(Integer.valueOf(6)), 1);
        Assert.assertEquals(theSet.lastIndexOf(Integer.valueOf(6)), 1);
    }
}

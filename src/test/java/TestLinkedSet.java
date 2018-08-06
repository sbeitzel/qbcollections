import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.qbcps.util.collections.LinkedSet;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for the LinkedSet class
 *
 * Copyright 11/15/15 by Stephen Beitzel
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
@Test(groups = {"all", "smoke", "collections"})
public class TestLinkedSet {
    @Test
    public void testAddCollection() {
        ArrayList<Integer> source = new ArrayList<>();
        for (int i = 0; i<10; i++) {
            source.add(Integer.valueOf(i));
        }
        LinkedSet<Integer> dest = new LinkedSet<>(source);
        Assert.assertEquals(dest.size(), source.size());
        for (Integer integer : source) {
            Assert.assertTrue(dest.contains(integer));
            Assert.assertEquals(dest.indexOf(integer), source.indexOf(integer));
        }
    }
}

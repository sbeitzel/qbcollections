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
}

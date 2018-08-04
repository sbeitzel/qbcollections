
/*
 * Copyright 8/3/18 by Stephen Beitzel
 */

import com.qbcps.util.collections.RRDIterable;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
@Test(groups = {"all", "smoke", "collections", "rrd"})
public class TestRRDIterable {

    private RRDIterable<Integer> _intList;

    /**
     * Make sure the list reports its size as the size allocated
     */
    public void testSize() {
        _intList = new RRDIterable<>(5);
        Assert.assertEquals(_intList.size(), 5);
    }

    /**
     * Make sure we can add elements to the list
     */
    @Test(dependsOnMethods = "testSize")
    public void testAdd() {
        _intList.add(Integer.valueOf(0));
        Assert.assertEquals(_intList.toString(), "[0, null, null, null, null]");
        _intList.add(Integer.valueOf(1));
        Assert.assertEquals(_intList.toString(), "[1, 0, null, null, null]");
        _intList.add(Integer.valueOf(2));
        Assert.assertEquals(_intList.toString(), "[2, 1, 0, null, null]");
        _intList.add(Integer.valueOf(3));
        Assert.assertEquals(_intList.toString(), "[3, 2, 1, 0, null]");
        _intList.add(Integer.valueOf(4));
        Assert.assertEquals(_intList.toString(), "[4, 3, 2, 1, 0]");
        _intList.add(Integer.valueOf(5));
        Assert.assertEquals(_intList.toString(), "[5, 4, 3, 2, 1]");
        Assert.assertEquals(_intList.size(), 5);
    }
}

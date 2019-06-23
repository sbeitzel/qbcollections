/*
 * Copyright 2019-06-22 by Stephen Beitzel
 */

import com.qbcps.util.collections.Trie;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test the functionality of the Trie data structure.
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
@Test(groups = {"all", "smoke", "collections", "trie"})
public class TestTrie {

    public void testAddAndFind() {
        Trie t = new Trie();
        t.addWord("something");
        Assert.assertTrue(t.isWord("something"));
        Assert.assertFalse(t.isPrefix("something"));
        Assert.assertTrue(t.isPrefix("some"));
    }
}

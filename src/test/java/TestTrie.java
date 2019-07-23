/*
 * Copyright 2019-06-22 by Stephen Beitzel
 */

import java.util.ArrayList;
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

    public void testCollect() {
        Trie t = new Trie();
        t.addWord("this");
        t.addWord("that");
        t.addWord("the");
        t.addWord("other");

        ArrayList<String> trieWords = new ArrayList<>();

        t.collect(trieWords);

        Assert.assertTrue(trieWords.contains("this"));
        Assert.assertTrue(trieWords.contains("that"));
        Assert.assertTrue(trieWords.contains("the"));
        Assert.assertTrue(trieWords.contains("other"));
    }

    public void testSubCollect() {
        Trie t = new Trie();
        t.addWord("this");
        t.addWord("that");
        t.addWord("the");
        t.addWord("other");

        ArrayList<String> trieWords = new ArrayList<>();

        t.collect("th", trieWords);

        Assert.assertTrue(trieWords.contains("this"));
        Assert.assertTrue(trieWords.contains("that"));
        Assert.assertTrue(trieWords.contains("the"));
        Assert.assertFalse(trieWords.contains("other"));
    }
}

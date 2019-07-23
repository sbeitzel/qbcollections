package com.qbcps.util.collections;
/*
 * Copyright 2019-06-22 by Stephen Beitzel
 */


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A naive (not thread-safe, not space-optimized) implementation of the Trie data structure,
 * based in large part on examples found around the web at various code tutorial websites.
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
public class Trie {
    private final TrieNode _root = new TrieNode();

    /**
     * Add all words in this Trie to the given collection.
     *
     * @param words a collection to which the words will be added
     */
    public void collect(Collection<String> words) {
        collect("", words);
    }

    /**
     * Collect all words from this Trie that begin with the specified prefix
     *
     * @param prefix the prefix
     * @param words collection to which the words will be added
     */
    public void collect(String prefix, Collection<String> words) {
        TrieNode n = findNode(prefix);
        if (n != null) {
            n.acceptCollector(prefix, words);
        }
    }

    public void addWord(String word) {
        TrieNode current = _root;

        for (int i=0; i<word.length(); i++) {
            current = current.getChildren().computeIfAbsent(word.substring(i, i+1), c -> new TrieNode());
        }
        current.setWord();
    }

    private TrieNode findNode(String word) {
        TrieNode current = _root;
        for (int i=0; i<word.length(); i++) {
            String ch = word.substring(i, i+1);
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current;
    }

    public boolean isWord(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isWord();
    }

    public boolean isPrefix(String prefix) {
        TrieNode node = findNode(prefix);
        return node != null && node.isPrefix();
    }
}

class TrieNode {
    private HashMap<String, TrieNode> _children = new HashMap<>();
    private boolean _isWord = false;

    HashMap<String, TrieNode> getChildren() {
        return _children;
    }

    void setWord() {
        _isWord = true;
    }

    boolean isWord() {
        return _isWord;
    }

    boolean isPrefix() {
        return !_children.isEmpty();
    }

    void acceptCollector(String prefix, Collection<String> words) {
        if (isWord()) {
            words.add(prefix);
        }
        for (Map.Entry<String, TrieNode> child : getChildren().entrySet()) {
            String cPrefix = prefix + child.getKey();
            child.getValue().acceptCollector(cPrefix, words);
        }
    }
}
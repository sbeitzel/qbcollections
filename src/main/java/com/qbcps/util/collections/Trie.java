package com.qbcps.util.collections;
/*
 * Copyright 2019-06-22 by Stephen Beitzel
 */


import java.util.HashMap;

/**
 * A naive (not thread-safe, not space-optimized) implementation of the Trie data structure,
 * based in large part on examples found around the web at various code tutorial websites.
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
public class Trie {
    private final TrieNode _root = new TrieNode();

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
}
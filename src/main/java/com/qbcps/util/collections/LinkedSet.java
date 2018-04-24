package com.qbcps.util.collections;
/*
 * Copyright 11/15/15 by Stephen Beitzel
 */

import java.util.Collection;
import java.util.LinkedList;

/**
 * ListSet backed by a LinkedList
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
public class LinkedSet<T> extends ListSet<T> {
    public LinkedSet() {
        super();
        _list = new LinkedList<>();
    }

    public LinkedSet(Collection<T> objects) {
        super();
        _list = new LinkedList<>();
        addAll(objects);
    }
}

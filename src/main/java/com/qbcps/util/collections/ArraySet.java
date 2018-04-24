package com.qbcps.util.collections;
/*
 * File copyright 8/14/13 by Stephen Beitzel
 */

import java.util.ArrayList;
import java.util.Collection;

/**
 * Set implementation that preserves the order of items as they are added.
 * Backing implementation is an ArrayList and a HashSet
 *
 * @author Stephen Beitzel &lt;sbeitzel@pobox.com&gt;
 */
public class ArraySet<T> extends ListSet<T> {

    public ArraySet() {
        super();
        _list = new ArrayList<>();
    }

    public ArraySet(Collection<T> objects) {
        super();
        _list = new ArrayList<>(objects.size());
        addAll(objects);
    }

}

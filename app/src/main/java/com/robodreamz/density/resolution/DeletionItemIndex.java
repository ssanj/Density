/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import java.util.concurrent.atomic.AtomicInteger;

public final class DeletionItemIndex {

    private int invalidIndex;
    private AtomicInteger index;

    public DeletionItemIndex(final int invalidIndex) {
        this.invalidIndex = invalidIndex;
        index = new AtomicInteger(invalidIndex);
    }

    public boolean isInvalid() {
        return index.get() == invalidIndex;
    }

    public void update(final int newIndex) {
        index.set(newIndex);
    }

    public boolean isValid() {
        return !isInvalid();
    }

    public int getValue() {
        return index.get();
    }

    public void reset() {
        index.set(invalidIndex);
    }
}

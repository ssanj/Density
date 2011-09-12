/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import java.util.concurrent.atomic.AtomicInteger;

public final class IndexPair {

    private final AtomicInteger prevIndex;
    private final AtomicInteger currentIndex;

    public IndexPair(final int prevIndex, final int currentIndex) {
        this.prevIndex = new AtomicInteger(prevIndex);
        this.currentIndex = new AtomicInteger(currentIndex);
    }

    public void update(int newIndex) {
        if (newIndex != currentIndex.get()) {
            prevIndex.set(currentIndex.get());
            currentIndex.set(newIndex);
        }
    }

    public boolean isNew(final int index) {
        return currentIndex.get() != index;
    }

    public int getCurrentIndex() {
        return currentIndex.get();
    }

    public int getPreviousIndex() {
        return prevIndex.get();
    }
}

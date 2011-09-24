/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class OnDeletionSelectedPositionFinderTest {

    private static final int INVALID_INDEX = -9999;
    private OnDeletionSelectedPositionFinder finder;

    @Before public void setup() {
        finder = new OnDeletionSelectedPositionFinder(INVALID_INDEX);
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheFirstItem() {
        assertEquals("Incorrect position returned", 0, finder.findSelectionPosition(2, 0));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsMiddleItem() {
        assertEquals("Incorrect position returned", 1, finder.findSelectionPosition(2, 1));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheLastItem() {
        assertEquals("Incorrect position returned", 1, finder.findSelectionPosition(2, 2));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheLastItemAndTheListIsEmpty() {
        assertEquals("Incorrect position returned", INVALID_INDEX, finder.findSelectionPosition(0, 5));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheLastItemAndTheListIsNotEmpty() {
        assertEquals("Incorrect position returned", 0, finder.findSelectionPosition(1, 1));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheFirstItemAndTheListIsNotEmpty() {
        assertEquals("Incorrect position returned", 0, finder.findSelectionPosition(1, 0));
    }
}

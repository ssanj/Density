/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class OnDeletionSelectedPositionFinderTest {

    private static final int INVALID_INDEX = -9999;
    private OnDeletionSelectedPositionFinder finder;

    @Before public void setup() {
        finder = new OnDeletionSelectedPositionFinder(INVALID_INDEX);
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheFirstItem() {
        assertValidPosition(0, finder.findSelectionPosition(2, 0));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsMiddleItem() {
        assertValidPosition(1, finder.findSelectionPosition(2, 1));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheLastItem() {
        assertValidPosition(1, finder.findSelectionPosition(2, 2));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheLastItemAndTheListIsEmpty() {
        assertInvalidPosition(finder.findSelectionPosition(0, 5));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheLastItemAndTheListIsNotEmpty() {
        assertValidPosition(0, finder.findSelectionPosition(1, 1));
    }

    @Test public void shouldFindSelectPositionWhenDeletedItemIsTheFirstItemAndTheListIsNotEmpty() {
        assertValidPosition(0, finder.findSelectionPosition(1, 0));
    }

    private void assertValidPosition(final int expected, final OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition result) {
        assertTrue("Should be valid", result.isValid());
        assertFalse("Should be valid", result.isInvalid());
        assertEquals("Incorrect position returned", expected, result.getPosition());
    }

    private void assertInvalidPosition(final OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition result) {
        assertTrue("Should be invalid", result.isInvalid());
        assertFalse("Should be invalid", result.isValid());
        assertEquals("Incorrect position returned", INVALID_INDEX, result.getPosition());
    }
}

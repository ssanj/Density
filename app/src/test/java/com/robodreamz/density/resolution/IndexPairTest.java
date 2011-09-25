/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class IndexPairTest {

    private static final int INVALID_INDEX = -2;
    private IndexPair indexPair;

    @Before public void setup() {
        indexPair = new IndexPair(INVALID_INDEX, -1);
    }

    @Test public void shouldInitializeWithTheValuesProvided() {
        assertPair(-2, -1);
    }

    @Test public void shouldUpdateBothPreviousAndCurrentOnReceivingANewValue() {
        assertPair(-2, -1);
        indexPair.update(1);
        assertPair(-1, 1);
        indexPair.update(0);
        assertPair(1, 0);
        indexPair.update(2);
        assertPair(0, 2);
        indexPair.update(-1);
        assertPair(2, -1);
    }

    @Test public void shouldNotUpdateIfTheNewValueIsTheSame() {
        assertPair(-2, -1);
        indexPair.update(-1);
        assertPair(-2, -1);
    }

    @Test public void shouldKnowIfAValueIsNew() {
        assertTrue("Should identify a new value", indexPair.isNew(6));
        assertFalse("Should identify an existing value", indexPair.isNew(-1));
        assertTrue("Should identify a new value", indexPair.isNew(-2));
    }

    @Test public void shouldKnowIfTheCurrentIndexIsInvalid() {
        indexPair.update(2);
        assertFalse("Should be valid", indexPair.isInvalid());
        assertTrue("Should be valid", indexPair.isValid());
        indexPair.update(-2);
        assertTrue("Should be invalid", indexPair.isInvalid());
        assertFalse("Should be invalid", indexPair.isValid());
    }

    @Test public void shouldResetCurrentIndex() {
        indexPair.update(2);
        assertPair(-1, 2);
        indexPair.resetCurrentIndex();
        assertPair(2, INVALID_INDEX);
    }

    private void assertPair(final int prev, final int cur) {
        assertEquals("Incorrect previous index", prev, indexPair.getPreviousIndex());
        assertEquals("Incorrect current index", cur, indexPair.getCurrentIndex());
    }
}

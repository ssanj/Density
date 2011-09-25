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

public final class DeletionItemIndexTest {

    private static final int INVALID_INDEX = -9999;
    private DeletionItemIndex index;

    @Before public void setup() {
        index = new DeletionItemIndex(INVALID_INDEX);
    }

    @Test public void shouldBeInvalidOnConstruction() {
        assertEquals("Incorrect value returned", INVALID_INDEX, index.getValue());
        assertTrue("Should be invalid", index.isInvalid());
        assertFalse("Should be invalid", index.isValid());
    }

    @Test public void shouldKnowWhenItIsInvalid() {
        assertTrue("Should be invalid", index.isInvalid());
        assertFalse("Should be invalid", index.isValid());
    }

    @Test public void shouldKnowWhenItIsValid() {
        index.update(5);
        assertFalse("Should be valid", index.isInvalid());
        assertTrue("Should be valid", index.isValid());
    }

    @Test public void shouldReturnItsValue() {
        assertEquals("Incorrect value returned", INVALID_INDEX, index.getValue());
        index.update(10);
        assertEquals("Incorrect value returned", 10, index.getValue());
    }

    @Test public void shouldUpdateItsValue() {
        index.update(2);
        assertEquals("Incorrect value returned", 2, index.getValue());
        index.update(6);
        assertEquals("Incorrect value returned", 6, index.getValue());
    }

    @Test public void shouldResetItsValue() {
        index.update(2);
        assertEquals("Incorrect value returned", 2, index.getValue());
        index.reset();
        assertEquals("Incorrect value returned", INVALID_INDEX, index.getValue());
        assertTrue("Should be invalid", index.isInvalid());
        assertFalse("Should be invalid", index.isValid());
    }
}

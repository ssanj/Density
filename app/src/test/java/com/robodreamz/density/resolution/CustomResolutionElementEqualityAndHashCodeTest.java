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
import static org.mockito.Mockito.mock;

public final class CustomResolutionElementEqualityAndHashCodeTest {

    private static final int WIDTH = 123;
    private static final int HEIGHT = 456;
    private CustomResolutionElement element;

    @Before public void setup() {
        element = new CustomResolutionElement(new StandardResolutionElement(WIDTH, HEIGHT));
    }

    @Test public void shouldEqualByIdentity() {
        assertTrue("Should equal itself", element.equals(element));
        assertEquals("Should have the same hashcode", element.hashCode(), element.hashCode());
    }

    @Test public void shouldEqualByClass() {
        final StandardResolutionElement mockElement = mock(StandardResolutionElement.class);
        assertFalse("Should equal same class with same values", element.equals(mockElement));
    }

    @Test public void shouldDelegateEqualityToDelegate() {
        final CustomResolutionElement element1 = new CustomResolutionElement(new StandardResolutionElement(WIDTH, HEIGHT));
        assertTrue("Should be equal", element.equals(element1));
        assertEquals("Should has same hashCode", element1.hashCode(), element.hashCode());
    }
}

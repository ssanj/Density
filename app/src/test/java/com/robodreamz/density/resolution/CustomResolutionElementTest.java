/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class CustomResolutionElementTest {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;

    private CustomResolutionElement element;

    @Before public void setup() {
        element = new CustomResolutionElement(WIDTH, HEIGHT);
    }

    @Test public void shouldImplementResolutionItem() {
        assertTrue("CustomResolutionElement does not implement ResolutionItem",
                ResolutionItem.class.isAssignableFrom(CustomResolutionElement.class));
    }

    @Test public void shouldReturnHeight() {
        assertEquals("Incorrect height returned", HEIGHT, element.getHeight());
    }

    @Test public void shouldReturnWidth() {
        assertEquals("Incorrect width returned", WIDTH, element.getWidth());
    }
}

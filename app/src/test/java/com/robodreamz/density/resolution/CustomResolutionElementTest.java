/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class CustomResolutionElementTest {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;

    private CustomResolutionElement element;
    private ResolutionElement mockResolutionElement;

    @Before public void setup() {
        mockResolutionElement = mock(ResolutionElement.class);
        element = new CustomResolutionElement(WIDTH, HEIGHT, mockResolutionElement);
    }

    @Test public void shouldImplementResolutionItem() {
        assertTrue("CustomResolutionElement does not implement ResolutionItem",
                ResolutionItem.class.isAssignableFrom(CustomResolutionElement.class));
    }

    @Test public void shouldReturnHeight() {
        when(mockResolutionElement.getHeight()).thenReturn(HEIGHT);
        assertEquals("Incorrect height returned", HEIGHT, element.getHeight());
        verify(mockResolutionElement).getHeight();
    }

    @Test public void shouldReturnWidth() {
        when(mockResolutionElement.getWidth()).thenReturn(WIDTH);
        assertEquals("Incorrect width returned", WIDTH, element.getWidth());
        verify(mockResolutionElement).getWidth();
    }
}

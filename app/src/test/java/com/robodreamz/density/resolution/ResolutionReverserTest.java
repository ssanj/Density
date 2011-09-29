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
import static org.mockito.Mockito.when;

public final class ResolutionReverserTest {

    private ResolutionReserver reverser;

    @Before public void setup() {
        reverser = new ResolutionReserver();
    }

    @Test public void shouldReverseAStandardResolutionElement() {
        expectResolutionElement(StandardResolutionElement.class, 500, 600);
    }

    @Test public void shouldReverseAResolutionElement() {
        expectResolutionElement(ResolutionElement.class, 440, 880);
    }

    @Test public void shouldReverseACustomResolutionElement() {
        expectResolutionElement(CustomResolutionElement.class, 120, 101);
    }

    private void expectResolutionElement(final Class<? extends ResolutionElement> clazz, final int width, final int height) {
        final ResolutionElement mockElement = mock(clazz);

        when(mockElement.getWidth()).thenReturn(width);
        when(mockElement.getHeight()).thenReturn(height);

        ResolutionElement result = reverser.reverse(mockElement);

        assertTrue("Incorrect resolution type returned", clazz.isAssignableFrom(result.getClass()));
        assertEquals("Incorrect width returned", height, result.getWidth());
        assertEquals("Incorrect height returned", width, result.getHeight());
    }
}

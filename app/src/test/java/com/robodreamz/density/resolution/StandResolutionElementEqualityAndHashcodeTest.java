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

public final class StandResolutionElementEqualityAndHashcodeTest {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 780;

    private StandardResolutionElement element;

    @Before public void setup() {
        element = new StandardResolutionElement(WIDTH, HEIGHT);
    }

    @Test public void shouldEqualByIdentity() {
        assertEquals("Elements are not equal", element, element);
        assertEquals("Elements should have the same hashcode", element.hashCode(), element.hashCode());
    }

    @Test public void shouldEqualByWidthAndHeight() {
        final StandardResolutionElement expected = new StandardResolutionElement(WIDTH, HEIGHT);
        assertEquals("Elements are not equal", expected, element);
        assertEquals("Elements should have the same hashcode", expected.hashCode(), element.hashCode());
    }

    @Test public void shouldNotEqualADifferingClass() {
        assertFalse("Elements should not be equal", new CustomResolutionElement(element).equals(element));
        assertFalse("Elements should not be equal", element.equals(null));
    }

    @Test public void shouldEqualByWidthAndHeightAndChecked() {
        element.check();

        final StandardResolutionElement expected = new StandardResolutionElement(WIDTH, HEIGHT);
        assertFalse("Elements should not be equal", expected.equals(element));

        expected.check();
        assertTrue("Elements should be equal", expected.equals(element));
        assertEquals("Elements should have the same hashcode", expected.hashCode(), element.hashCode());
    }

    @Test public void shouldEqualByWidthAndHeightAndMarkedForDeletion() {
        element.markedForDeletion();

        final StandardResolutionElement expected = new StandardResolutionElement(WIDTH, HEIGHT);
        assertFalse("Elements should not be equal", expected.equals(element));

        expected.markedForDeletion();
        assertTrue("Elements should be equal", expected.equals(element));
        assertEquals("Elements should have the same hashcode", expected.hashCode(), element.hashCode());
    }

    @Test public void shouldEqualByWidthAndHeightAndLayoutId() {
        element.setElementLayoutId(5000);

        final StandardResolutionElement expected = new StandardResolutionElement(WIDTH, HEIGHT);
        assertFalse("Elements should not be equal", expected.equals(element));

        expected.setElementLayoutId(5000);
        assertTrue("Elements should be equal", expected.equals(element));
        assertEquals("Elements should have the same hashcode", expected.hashCode(), element.hashCode());
    }

    @Test public void shouldEqualByWidthAndViewType() {
        element.setViewType(ResolutionItem.ViewType.CUSTOM_ELEMENT);

        final StandardResolutionElement expected = new StandardResolutionElement(WIDTH, HEIGHT);
        assertFalse("Elements should not be equal", expected.equals(element));

        expected.setViewType(ResolutionItem.ViewType.CUSTOM_ELEMENT);
        assertTrue("Elements should be equal", expected.equals(element));
        assertEquals("Elements should have the same hashcode", expected.hashCode(), element.hashCode());
    }

    @Test public void shouldEqualByWidthAndHeightAndCheckedAndMarkedForDeletionAndLayoutIdAndViewType() {
        element.check();
        element.markedForDeletion();
        element.setElementLayoutId(1000);
        element.setViewType(ResolutionItem.ViewType.ELEMENT);

        final StandardResolutionElement expected = new StandardResolutionElement(WIDTH, HEIGHT);
        assertFalse("Elements should not be equal", expected.equals(element));

        expected.check();
        expected.markedForDeletion();
        expected.setElementLayoutId(1000);
        expected.setViewType(ResolutionItem.ViewType.ELEMENT);
        assertTrue("Elements should be equal", expected.equals(element));
        assertEquals("Elements should have the same hashcode", expected.hashCode(), element.hashCode());
    }
}

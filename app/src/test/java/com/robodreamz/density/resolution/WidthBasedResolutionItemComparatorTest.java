/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public final class WidthBasedResolutionItemComparatorTest {

    private WidthBasedResolutionItemComparator comparator;
    private ResolutionElement mockElement1;
    private ResolutionElement mockElement2;

    @Before public void setup() {
        comparator = new WidthBasedResolutionItemComparator();
        mockElement1 = mock(ResolutionElement.class);
        mockElement2 = mock(ResolutionElement.class);
    }

    @Test public void shouldImplementComparator() {
        assertTrue("Should implement Comparator", Comparator.class.isAssignableFrom(WidthBasedResolutionItemComparator.class));
    }

    @Test public void shouldPutAResolutionItemThatIsNotAResolutionElementBeforeOneThatIs() {
        final ResolutionItem mockHeader = mock(ResolutionItem.class);
        assertEquals("Non-ResolutionElements should come first", -1, comparator.compare(mockHeader, mockElement2));
        verifyZeroInteractions(mockElement2);
    }

    @Test public void shouldPutAResolutionItemThatIsAResolutionElementAfterOneThatIsNot() {
        final ResolutionItem mockHeader = mock(ResolutionItem.class);
        assertEquals("Non-ResolutionElements should come first", 1, comparator.compare(mockElement2, mockHeader));
        verifyZeroInteractions(mockElement2);
    }

    @Test public void shouldPutAResolutionWithALowerWidthBefore() {
        when(mockElement1.getWidth()).thenReturn(200);
        when(mockElement2.getWidth()).thenReturn(300);

        assertEquals("Smaller resolution width should come first", -1, comparator.compare(mockElement1, mockElement2));

        verify(mockElement1).getWidth();
        verify(mockElement2).getWidth();
    }

    @Test public void shouldPutAResolutionWithAHigherWidthAfterOneWithALowerWidth() {
        when(mockElement1.getWidth()).thenReturn(300);
        when(mockElement2.getWidth()).thenReturn(200);

        assertEquals("Smaller resolution width should come first", 1, comparator.compare(mockElement1, mockElement2));

        verify(mockElement1).getWidth();
        verify(mockElement2).getWidth();
    }

    @Test public void shouldPutAResolutionWithEqualWidthTogether() {
        when(mockElement1.getWidth()).thenReturn(300);
        when(mockElement2.getWidth()).thenReturn(300);

        assertEquals("Resolution with equal width should be together", 0, comparator.compare(mockElement1, mockElement2));

        verify(mockElement1).getWidth();
        verify(mockElement2).getWidth();
    }

    @Test public void shouldImplementEqualsOnlyForTheComparator() {
        assertTrue("Should have been equal", comparator.equals(comparator));
        assertTrue("Should have been equal", comparator.equals(new WidthBasedResolutionItemComparator()));
        assertFalse("Should not have been equal", comparator.equals(null));
        assertFalse("Should not have been equal", comparator.equals(new DummyComparator()));
    }

    private static final class DummyComparator implements Comparator<ResolutionItem> {
        @Override public int compare(final ResolutionItem resolutionItem, final ResolutionItem resolutionItem1) {
            return 0;
        }
    }
}

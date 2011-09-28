/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class ResolutionListAdapterWithDeletionsTest {

    private static final int DELETION_POSITION = 5;

    private ResolutionListAdapter adapterSpy;
    private ResolutionItem mockResolutionItemHeader;
    private ResolutionItem mockResolutionItemElement1;
    private ResolutionItem mockResolutionItemElement2;
    private ResolutionItem mockResolutionItemElement3;
    private ResolutionItem mockResolutionItemElement4;
    private ResolutionItem mockResolutionItemElement5;
    private List<ResolutionItem> resolutionItemsMocks;

    @Before public void setUp() throws Exception {
        mockResolutionItemHeader = mock(ResolutionItem.class);
        mockResolutionItemElement1 = mock(ResolutionItem.class);
        mockResolutionItemElement2 = mock(ResolutionItem.class);
        mockResolutionItemElement3 = mock(ResolutionItem.class);
        mockResolutionItemElement4 = mock(ResolutionItem.class);
        mockResolutionItemElement5 = mock(ResolutionItem.class);
        ResolutionData.INDEX_PAIR.update(DELETION_POSITION);
        ResolutionData.DELETION_INDEX.update(DELETION_POSITION);
        resolutionItemsMocks = createResolutionItems();
        adapterSpy = spy(new ResolutionListAdapter(null, null, resolutionItemsMocks, null));
    }

    @After public void teardown() {
        ResolutionData.INDEX_PAIR.reset();
        ResolutionData.DELETION_INDEX.reset();
    }

    @Test public void shouldShouldRemoveASelectedItemWhenItIsACustomElement() {
        assertEquals("Incorrect selected index", DELETION_POSITION, ResolutionData.INDEX_PAIR.getCurrentIndex());
        assertEquals("Incorrect deletion index", DELETION_POSITION, ResolutionData.DELETION_INDEX.getValue());
        assertEquals("Incorrect resolution list size", 6, adapterSpy.getCount());
        doNothing().when(adapterSpy).notifyDataSetChanged();
        when(mockResolutionItemElement5.getViewType()).thenReturn(ResolutionItem.ViewType.CUSTOM_ELEMENT);

        adapterSpy.removeItem(DELETION_POSITION);

        assertEquals("Incorrect resolution list size", 5, adapterSpy.getCount());
        assertTrue("selection was not reset", ResolutionData.INDEX_PAIR.isInvalid());
        assertTrue("deletion index was not reset", ResolutionData.DELETION_INDEX.isInvalid());

        verify(adapterSpy).notifyDataSetChanged();
    }

    @Test public void shouldShouldNotRemoveASelectedItemWhenItIsNotACustomElement() {
        assertEquals("Incorrect selected index", DELETION_POSITION, ResolutionData.INDEX_PAIR.getCurrentIndex());
        assertEquals("Incorrect deletion index", DELETION_POSITION, ResolutionData.DELETION_INDEX.getValue());
        assertEquals("Incorrect resolution list size", 6, adapterSpy.getCount());
        when(mockResolutionItemElement5.getViewType()).thenReturn(ResolutionItem.ViewType.ELEMENT);

        adapterSpy.removeItem(DELETION_POSITION);

        assertEquals("Incorrect resolution list size", 6, adapterSpy.getCount());
        assertEquals("Incorrect selection index", DELETION_POSITION, ResolutionData.INDEX_PAIR.getCurrentIndex());
        assertEquals("Incorrect deletion index", DELETION_POSITION, ResolutionData.DELETION_INDEX.getValue());

        verify(adapterSpy, never()).notifyDataSetChanged();
    }

    @Test public void shouldMarkACustomElementForDeletion() {
        final int currentDeletionIndex = 1000;
        ResolutionData.DELETION_INDEX.update(currentDeletionIndex);
        assertEquals("Incorrect DELETION INDEX", currentDeletionIndex, ResolutionData.DELETION_INDEX.getValue());
        when(mockResolutionItemElement5.getViewType()).thenReturn(ResolutionItem.ViewType.CUSTOM_ELEMENT);
        doNothing().when(adapterSpy).notifyDataSetChanged();

        adapterSpy.markForDeletion(DELETION_POSITION);

        assertEquals("Incorrect DELETION INDEX", DELETION_POSITION, ResolutionData.DELETION_INDEX.getValue());

        verify(mockResolutionItemElement5).markedForDeletion();
        verify(adapterSpy).notifyDataSetChanged();
    }

    @Test public void shouldNotMarkANonCustomElementForDeletion() {
        final int currentDeletionIndex = 1000;
        ResolutionData.DELETION_INDEX.update(currentDeletionIndex);
        assertEquals("Incorrect DELETION INDEX", currentDeletionIndex, ResolutionData.DELETION_INDEX.getValue());
        when(mockResolutionItemElement5.getViewType()).thenReturn(ResolutionItem.ViewType.ELEMENT);

        adapterSpy.markForDeletion(DELETION_POSITION);

        assertEquals("Incorrect DELETION INDEX", currentDeletionIndex, ResolutionData.DELETION_INDEX.getValue());

        verify(mockResolutionItemElement5, never()).markedForDeletion();
        verify(adapterSpy, never()).notifyDataSetChanged();
    }

    @Test public void shouldUnmarkACustomElementFromDeletion() {
        final int currentDeletionIndex = 1000;
        ResolutionData.DELETION_INDEX.update(currentDeletionIndex);
        assertEquals("Incorrect DELETION INDEX", currentDeletionIndex, ResolutionData.DELETION_INDEX.getValue());
        when(mockResolutionItemElement5.getViewType()).thenReturn(ResolutionItem.ViewType.CUSTOM_ELEMENT);
        doNothing().when(adapterSpy).notifyDataSetChanged();

        adapterSpy.unmarkForDeletion(DELETION_POSITION);

        assertTrue("Deletion index has not been reset", ResolutionData.DELETION_INDEX.isInvalid());
        verify(mockResolutionItemElement5).unmarkForDeletion();
        verify(adapterSpy).notifyDataSetChanged();
    }

    @Test public void shouldNotUnmarkANonCustomElementFromDeletion() {
        final int currentDeletionIndex = 1000;
        ResolutionData.DELETION_INDEX.update(currentDeletionIndex);
        assertEquals("Incorrect DELETION INDEX", currentDeletionIndex, ResolutionData.DELETION_INDEX.getValue());
        when(mockResolutionItemElement5.getViewType()).thenReturn(ResolutionItem.ViewType.ELEMENT);

        adapterSpy.unmarkForDeletion(DELETION_POSITION);

        assertFalse("Deletion index should not have been reset", ResolutionData.DELETION_INDEX.isInvalid());
        verify(mockResolutionItemElement5, never()).unmarkForDeletion();
        verify(adapterSpy, never()).notifyDataSetChanged();
    }

    private List<ResolutionItem> createResolutionItems() {
        return Arrays.asList (
                mockResolutionItemHeader,
                mockResolutionItemElement1,
                mockResolutionItemElement2,
                mockResolutionItemElement3,
                mockResolutionItemElement4,
                mockResolutionItemElement5);
    }

}

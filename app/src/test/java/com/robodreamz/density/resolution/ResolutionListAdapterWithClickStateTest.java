/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class ResolutionListAdapterWithClickStateTest {

    private static final int PREVIOUSLY_SELECTED_INDEX = 1;

    private LayoutInflaterDelegate mockLayoutInflater;
    private DelegateFactory mockDelegateFactory;
    private ResolutionListAdapter adapterSpy;
    private ResolutionItem mockResolutionItemHeader;
    private ResolutionItem mockResolutionItemElement1;
    private ResolutionItem mockResolutionItemElement2;
    private ResolutionItem mockResolutionItemElement3;
    private ResolutionItem mockResolutionItemElement4;
    private ResolutionItem mockResolutionItemElement5;
    private Constants mockConstants;
    private int oldCurrentIndex;
    private List<ResolutionItem> mockResolutionItems;

    @Before public void setUp() throws Exception {
        mockLayoutInflater = mock(LayoutInflaterDelegate.class);
        mockDelegateFactory = mock(DelegateFactory.class);
        mockResolutionItemHeader = mock(ResolutionItem.class);
        mockResolutionItemElement1 = mock(ResolutionItem.class);
        mockResolutionItemElement2 = mock(ResolutionItem.class);
        mockResolutionItemElement3 = mock(ResolutionItem.class);
        mockResolutionItemElement4 = mock(ResolutionItem.class);
        mockResolutionItemElement5 = mock(ResolutionItem.class);
        mockConstants = mock(Constants.class);
        oldCurrentIndex = ResolutionData.INDEX_PAIR.getCurrentIndex();
        ResolutionData.INDEX_PAIR.update(PREVIOUSLY_SELECTED_INDEX);
        mockResolutionItems = createResolutionItems();
        adapterSpy = spy(new ResolutionListAdapter(mockLayoutInflater, mockDelegateFactory, mockResolutionItems, mockConstants));
    }

    @After public void teardown() {
        ResolutionData.INDEX_PAIR.update(oldCurrentIndex);
    }

    @Test public void shouldCheckTheSelectedPositionWhenItDoesNotHaveAPreviousSelection() {
        when(mockConstants.isInvalidPosition(PREVIOUSLY_SELECTED_INDEX)).thenReturn(true);

        adapterSpy.clickedItem(3);

        verify(mockResolutionItemElement3).check();
        verify(adapterSpy).notifyDataSetChanged();

        assertEquals("Incorrect current index position", 3, ResolutionData.INDEX_PAIR.getCurrentIndex());
    }

    @Test public void shouldCheckTheSelectedPositionWhenItHasAPreviousSelection() {
        when(mockConstants.isInvalidPosition(PREVIOUSLY_SELECTED_INDEX)).thenReturn(false);

        adapterSpy.clickedItem(2);

        verify(mockResolutionItemElement1).uncheck();
        verify(mockResolutionItemElement2).check();
        verify(adapterSpy).notifyDataSetChanged();

        assertEquals("Incorrect current index position", 2, ResolutionData.INDEX_PAIR.getCurrentIndex());
    }

    @Test public void shouldNotCheckAnythingIfTheNewPositionIsEqualToTheOldPosition() {
        adapterSpy.clickedItem(PREVIOUSLY_SELECTED_INDEX);

        verifyZeroInteractions(mockConstants);

        for (ResolutionItem mockRI : mockResolutionItems) {
            verifyZeroInteractions(mockRI);
        }

        verify(adapterSpy, never()).notifyDataSetChanged();
        assertEquals("Incorrect current index position", PREVIOUSLY_SELECTED_INDEX, ResolutionData.INDEX_PAIR.getCurrentIndex());
    }

    @Test public void shouldResetCheckStateIfThereWasAPreviousState() {
        final int invalidIndex = -999;
        when(mockConstants.getInvalidPositionIndex()).thenReturn(invalidIndex);
         ResolutionData.INDEX_PAIR.update(2);
         when(mockConstants.isInvalidPosition(PREVIOUSLY_SELECTED_INDEX)).thenReturn(false);

        adapterSpy.resetClick();

        verify(mockResolutionItemElement2).uncheck();
        verify(adapterSpy).notifyDataSetChanged();

        assertEquals("Incorrect current index position", invalidIndex, ResolutionData.INDEX_PAIR.getCurrentIndex());
    }

    @Test public void shouldNotResetCheckStateIfThereWasNoPreviousState() {
         when(mockConstants.isInvalidPosition(PREVIOUSLY_SELECTED_INDEX)).thenReturn(true);

        adapterSpy.resetClick();

        for (ResolutionItem mockRI : mockResolutionItems) {
            verifyZeroInteractions(mockRI);
        }

        verify(adapterSpy, never()).notifyDataSetChanged();
        verify(mockConstants, never()).getInvalidPositionIndex();
        assertEquals("Incorrect current index position", PREVIOUSLY_SELECTED_INDEX, ResolutionData.INDEX_PAIR.getCurrentIndex());
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

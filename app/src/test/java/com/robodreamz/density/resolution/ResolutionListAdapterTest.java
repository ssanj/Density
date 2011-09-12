/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.view.View;
import android.view.ViewGroup;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class ResolutionListAdapterTest {

    private LayoutInflaterDelegate mockLayoutInflater;
    private DelegateFactory mockDelegateFactory;
    private ResolutionListAdapter adapter;
    private ResolutionItem mockResolutionItemHeader;
    private ResolutionItem mockResolutionItemElement0;
    private ResolutionItem mockResolutionItemElement1;

    @Before public void setUp() throws Exception {
        mockLayoutInflater = mock(LayoutInflaterDelegate.class);
        mockDelegateFactory = mock(DelegateFactory.class);
        mockResolutionItemHeader = mock(ResolutionItem.class);
        mockResolutionItemElement0 = mock(ResolutionItem.class);
        mockResolutionItemElement1 = mock(ResolutionItem.class);
        adapter = new ResolutionListAdapter(mockLayoutInflater, mockDelegateFactory, createResolutionItems());
    }

    @Test public void shouldReturnTheNumberOfDataItems() throws Exception {
        assertThat("number of items returned is wrong.", adapter.getCount(), equalTo(3));
    }

    @Test public void shouldReturnTheRequestItem() throws Exception {
        assertThat("Incorrect item at index 0", (ResolutionItem) adapter.getItem(0), sameInstance(mockResolutionItemHeader));
        assertThat("Incorrect item at index 1", (ResolutionItem) adapter.getItem(1), sameInstance(mockResolutionItemElement0));
        assertThat("Incorrect item at index 2", (ResolutionItem) adapter.getItem(2), sameInstance(mockResolutionItemElement1));
    }

    @Test public void shouldReturnTheRequestedItemId() throws Exception {
        assertThat("Incorrect index for item at index 0", adapter.getItemId(0), equalTo(0l));
        assertThat("Incorrect index for item at index 1", adapter.getItemId(1), equalTo(1l));
        assertThat("Incorrect index for item at index 2", adapter.getItemId(2), equalTo(2l));
    }

    @Test public void shouldReturnANewView() throws Exception {
        assertCreatesView(null);
    }

    @Test public void shouldReuseAnExistingView() throws Exception {
        final View mockViewInput = mock(View.class);
        assertCreatesView(mockViewInput);
    }

    @Test public void shouldDisabledAllItemsExceptThoseEnabledExplicitly() {
        assertFalse("All items should be disabled except those that return true from isEnabled.", adapter.areAllItemsEnabled());
    }

    private ResolutionItem[] createResolutionItems() {
        return new ResolutionItem[] {mockResolutionItemHeader, mockResolutionItemElement0, mockResolutionItemElement1};
    }

    private void assertCreatesView(final View viewInput) {
        final ViewDelegate mockViewDelegateInput = mock(ViewDelegate.class);
        final ViewDelegate mockViewDelegateResult = mock(ViewDelegate.class);
        final View mockViewOutput = mock(View.class);
        final ViewGroup mockGroupInput = mock(ViewGroup.class);

        when(mockDelegateFactory.createViewDelegate(viewInput)).thenReturn(mockViewDelegateInput);
        when(mockResolutionItemHeader.getView(mockLayoutInflater, mockViewDelegateInput)).thenReturn(mockViewDelegateResult);
        when(mockViewDelegateResult.getDelegate()).thenReturn(mockViewOutput);

        assertThat("Incorrect view returned for header", adapter.getView(0, viewInput, mockGroupInput), sameInstance(mockViewOutput));
    }

    @Test public void shouldReturnIfEnabled() throws Exception {
        when(mockResolutionItemHeader.isEnabled()).thenReturn(false);
        when(mockResolutionItemElement0.isEnabled()).thenReturn(true);
        when(mockResolutionItemElement1.isEnabled()).thenReturn(false);

        assertFalse("Header element should be disabled", adapter.isEnabled(0));
        assertTrue("Element 1 should be enabled", adapter.isEnabled(1));
        assertFalse("Element 2 should be disabled", adapter.isEnabled(2));
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.widget.AdapterView;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.screen.ClickableItemsListAdapter;
import com.robodreamz.density.screen.DefaultDensity;
import com.robodreamz.density.screen.DensityResultCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class ResolutionListSelectListener {

    private ResolutionListFragmentSetup.ResolutionListSelectListener listener;
    private ListViewDelegate mockListViewDelegate;
    private DefaultDensity mockDefaultDensity;
    private DensityResultCalculator mockCalculator;

    @Before public void setup() {
        mockListViewDelegate = mock(ListViewDelegate.class);
        mockDefaultDensity = mock(DefaultDensity.class);
        mockCalculator = mock(DensityResultCalculator.class);
        listener = new ResolutionListFragmentSetup.ResolutionListSelectListener(mockListViewDelegate, mockCalculator, mockDefaultDensity);
    }

    @Test public void shouldImplementOnItemSelected() {
        assertTrue("ResolutionListFragmentSetup.ResolutionListSelectListener should implement AdapterView.OnItemSelectedListener",
                AdapterView.OnItemSelectedListener.class.isAssignableFrom(listener.getClass()));
    }

    @Test public void shouldUpdateResultOnSelection() {
        listener.doOnItemSelected(10);
        verify(mockCalculator).calculateDensity(10, mockListViewDelegate);
    }

    @Test public void shouldUpdateDisplayWhenThereIsNoSelection() {
        final ClickableItemsListAdapter mockAdapter = mock(ClickableItemsListAdapter.class);
        when(mockListViewDelegate.getAdapter()).thenReturn(mockAdapter);

        listener.doOnNothingSelected();
        verify(mockDefaultDensity).setValue();
        verify(mockAdapter).resetClick();
    }
}

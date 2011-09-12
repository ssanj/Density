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
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public final class ResolutionListSelectListenerTest {

    private ResolutionListFragmentSetup.ResolutionListSelectListener listener;
    private ListViewDelegate mockListViewDelegate;
    private DensityResultCalculator mockCalculator;

    @Before public void setup() {
        mockListViewDelegate = mock(ListViewDelegate.class);
        mockCalculator = mock(DensityResultCalculator.class);
        listener = new ResolutionListFragmentSetup.ResolutionListSelectListener(mockListViewDelegate, mockCalculator);
    }

    @Test public void shouldImplementOnItemSelected() {
        assertTrue("ResolutionListFragmentSetup.ResolutionListSelectListener should implement AdapterView.OnItemSelectedListener",
                AdapterView.OnItemSelectedListener.class.isAssignableFrom(listener.getClass()));
    }

    @Test public void shouldUpdateResultOnSelection() {
        listener.doOnItemSelected(10);
        verify(mockCalculator).calculateDensity(10, mockListViewDelegate);
    }

    @Test public void shouldDoNothingWhenThereIsNoSelection() {
        listener.doOnNothingSelected();

        verifyZeroInteractions(mockListViewDelegate);
        verifyZeroInteractions(mockCalculator);
    }
}

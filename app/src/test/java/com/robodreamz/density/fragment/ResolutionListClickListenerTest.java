/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.screen.DensityResultCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class ResolutionListClickListenerTest {

    private ListViewDelegate mockListView;
    private ResolutionListFragmentSetup.ResolutionListClickListener listener;
    private DensityResultCalculator mockCalculator;


    @Before public void setup() throws Exception {
         mockListView = mock(ListViewDelegate.class);
        mockCalculator = mock(DensityResultCalculator.class);
        listener = new ResolutionListFragmentSetup.ResolutionListClickListener(mockCalculator, mockListView);
    }

    @Test public void shouldCalculateResult() {
        listener.doOnItemClick(5);
        verify(mockCalculator).calculateDensity(5, mockListView);
    }
}

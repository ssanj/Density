/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DefaultDensity;
import com.robodreamz.density.screen.DensityResultCalculator;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class ResolutionListFragmentSetupTest {

    private ActivityDelegate mockActivity;
    private ResolutionListFragmentSetup fragmentSetup;
    private DefaultDensity mockDefaultDensity;
    private DensityResultCalculator mockDensityResultCalculator;

    @Before public void setup() throws Exception {
        mockActivity = mock(ActivityDelegate.class);
        mockDefaultDensity = mock(DefaultDensity.class);
        mockDensityResultCalculator = mock(DensityResultCalculator.class);
        fragmentSetup = new ResolutionListFragmentSetup(mockActivity, mockDensityResultCalculator, mockDefaultDensity);
    }

    @Test public void shouldInitializeResolutionList() throws Exception {
        final ListViewDelegate mockList = mock(ListViewDelegate.class);
        final LayoutInflaterDelegate mockLayoutInflater = mock(LayoutInflaterDelegate.class);
        when(mockActivity.findViewById(R.id.app_screen_resolution_list)).thenReturn(mockList);
        when(mockActivity.getLayoutInflater()).thenReturn(mockLayoutInflater);

        fragmentSetup.setup();

        verify(mockList).setAdapter(isA(ResolutionListAdapter.class));
        verify(mockList).setOnItemClickListener(isA(ResolutionListFragmentSetup.ResolutionListClickListener.class));
        verify(mockList).setOnItemSelectedListener(isA(ResolutionListFragmentSetup.ResolutionListSelectListener.class));
        verifyZeroInteractions(mockLayoutInflater);
    }
}

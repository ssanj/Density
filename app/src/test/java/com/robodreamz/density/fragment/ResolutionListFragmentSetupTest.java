/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.ClickableItemsListAdapter;
import com.robodreamz.density.screen.DensityResultCalculator;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
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

    private static final int SELECTED_INDEX = 5;

    private ActivityDelegate mockActivity;
    private ResolutionListFragmentSetup fragmentSetup;
    private DensityResultCalculator mockDensityResultCalculator;
    private int oldCurrentIndex;

    @Before public void setup() throws Exception {
        mockActivity = mock(ActivityDelegate.class);
        mockDensityResultCalculator = mock(DensityResultCalculator.class);
        oldCurrentIndex = ResolutionData.INDEX_PAIR.getCurrentIndex();
        ResolutionData.INDEX_PAIR.update(SELECTED_INDEX);
        fragmentSetup = new ResolutionListFragmentSetup(mockActivity, mockDensityResultCalculator);
    }

    @After public void teardown() {
        ResolutionData.INDEX_PAIR.update(oldCurrentIndex);
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

    @Test public void shouldResumeWithPreviouslySelectedIndexIfValidAndInTouchMode() {
        final Constants mockConstants = mock(Constants.class);
        final ListViewDelegate mockList = mock(ListViewDelegate.class);
        final ClickableItemsListAdapter mockAdapter = mock(ClickableItemsListAdapter.class);

        when(mockActivity.findViewById(R.id.app_screen_resolution_list)).thenReturn(mockList);
        when(mockConstants.isInvalidPosition(SELECTED_INDEX)).thenReturn(false);
        when(mockList.getAdapter()).thenReturn(mockAdapter);
        when(mockList.isInTouchMode()).thenReturn(true);

        fragmentSetup.onResume(mockConstants);

        verify(mockList).setSelection(SELECTED_INDEX);
        verify(mockAdapter).clickedItem(SELECTED_INDEX);
        verify(mockDensityResultCalculator).calculateDensity(SELECTED_INDEX, mockList);
    }

    @Test public void shouldResumeWithPreviouslySelectedIndexIfValidAndNotInTouchMode() {
        final Constants mockConstants = mock(Constants.class);
        final ListViewDelegate mockList = mock(ListViewDelegate.class);
        final ClickableItemsListAdapter mockAdapter = mock(ClickableItemsListAdapter.class);

        when(mockActivity.findViewById(R.id.app_screen_resolution_list)).thenReturn(mockList);
        when(mockConstants.isInvalidPosition(SELECTED_INDEX)).thenReturn(false);
        when(mockList.getAdapter()).thenReturn(mockAdapter);
        when(mockList.isInTouchMode()).thenReturn(false);

        fragmentSetup.onResume(mockConstants);

        verify(mockList).setSelection(SELECTED_INDEX);
        verify(mockAdapter).clickedItem(SELECTED_INDEX);
        verifyZeroInteractions(mockDensityResultCalculator);
    }

    @Test public void shouldResumeWithPreviouslySelectedIndexIfInvalid() {
        final Constants mockConstants = mock(Constants.class);
        final ListViewDelegate mockList = mock(ListViewDelegate.class);
        final ClickableItemsListAdapter mockAdapter = mock(ClickableItemsListAdapter.class);

        when(mockActivity.findViewById(R.id.app_screen_resolution_list)).thenReturn(mockList);
        when(mockConstants.isInvalidPosition(SELECTED_INDEX)).thenReturn(true);
        fragmentSetup.onResume(mockConstants);

        verifyZeroInteractions(mockList);
        verifyZeroInteractions(mockAdapter);
        verifyZeroInteractions(mockDensityResultCalculator);
    }
}

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

    @Before public void setup() {
        mockActivity = mock(ActivityDelegate.class);
        fragmentSetup = new ResolutionListFragmentSetup(mockActivity);
    }

    @Test public void shouldInitializeResolutionList() throws Exception {
        final ListViewDelegate mockList = mock(ListViewDelegate.class);
        final LayoutInflaterDelegate mockLayoutInflater = mock(LayoutInflaterDelegate.class);
        when(mockActivity.findViewById(R.id.app_screen_resolution_list)).thenReturn(mockList);
        when(mockActivity.getLayoutInflater()).thenReturn(mockLayoutInflater);

        fragmentSetup.setup();

        verify(mockList).setAdapter(isA(ResolutionListAdapter.class));
        verify(mockList).setOnItemClickListener(isA(ResolutionListFragmentSetup.ResolutionListClickListener.class));
        verifyZeroInteractions(mockLayoutInflater);
    }
}

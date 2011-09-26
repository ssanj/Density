/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.widget.AdapterView;
import com.robodreamz.density.DensityApplication;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionItem;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DensityResultCalculator;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class ResolutionListLongClickListenerTest {

    private static final int CLICK_POSITION = 5;
    private ListViewDelegate mockResolutionList;
    private ActivityDelegate mockActivity;
    private ResolutionListFragmentSetup.ResolutionListLongClickListener listener;
    private DensityResultCalculator mockCalculator;

    @Before public void setup() {
        mockResolutionList = mock(ListViewDelegate.class);
        mockActivity = mock(ActivityDelegate.class);
        mockCalculator = mock(DensityResultCalculator.class);
        ResolutionData.DELETION_INDEX.reset();
        listener = new ResolutionListFragmentSetup.ResolutionListLongClickListener(mockResolutionList, mockActivity, mockCalculator);
    }

    @After public void teardown() {
        ResolutionData.DELETION_INDEX.reset();
    }

    @Test public void shouldImplementOnItemLongClickListener() {
        assertTrue("Should implement AdapterView.OnItemLongClickListener",
                AdapterView.OnItemLongClickListener.class.isAssignableFrom(ResolutionListFragmentSetup.ResolutionListLongClickListener.class));
    }

    @Test public void shouldPerformLongItemClickOnCustomResolutions() {
        final ResolutionListAdapter spyAdapter = spy(new ResolutionListAdapter(null, null, new ArrayList<ResolutionItem>(), null));
        final ResolutionElement mockElement = mock(ResolutionElement.class);

        when(mockResolutionList.getAdapter()).thenReturn(spyAdapter);
        doReturn(mockElement).when(spyAdapter).getItem(CLICK_POSITION);
        doNothing().when(spyAdapter).markForDeletion(CLICK_POSITION);
        when(mockElement.getViewType()).thenReturn(ResolutionItem.ViewType.CUSTOM_ELEMENT);

        final boolean result = listener.doOnItemLongClick(CLICK_POSITION);

        assertTrue("Expected the event to be consumeed", result);

        verify(mockActivity).showDialog(DensityApplication.DENSITY_APP_DELETE_RESOLUTION_DIALOG);
        verify(mockCalculator).calculateDensity(CLICK_POSITION, mockResolutionList);
        verify(mockResolutionList).setSelection(CLICK_POSITION);
    }

    @Test public void shouldNotPerformLongItemClickOnOtherResolutions() {
        final ResolutionListAdapter spyAdapter = spy(new ResolutionListAdapter(null, null, new ArrayList<ResolutionItem>(), null));
        final ResolutionElement mockElement = mock(ResolutionElement.class);

        when(mockResolutionList.getAdapter()).thenReturn(spyAdapter);
        doReturn(mockElement).when(spyAdapter).getItem(CLICK_POSITION);
        doNothing().when(spyAdapter).markForDeletion(CLICK_POSITION);
        when(mockElement.getViewType()).thenReturn(ResolutionItem.ViewType.ELEMENT);

        final boolean result = listener.doOnItemLongClick(CLICK_POSITION);

        assertFalse("Expected the event to be not consumed", result);
        assertTrue("Incorrect deletion index", ResolutionData.DELETION_INDEX.isInvalid());

        verifyZeroInteractions(mockActivity);
        verify(spyAdapter, never()).markForDeletion(CLICK_POSITION);
        verifyZeroInteractions(mockCalculator);
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.Dialog;
import android.widget.ListView;
import android.widget.TextView;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionItem;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class DensityAppWithDialogsTest {

    private DensityAppActivity spyActivity;

    @Before public void setup() {
        spyActivity = spy(new DensityAppActivity());
        spyActivity.onCreate(null);
    }

    @After public void teardown() {
        ResolutionData.DELETION_INDEX.reset();
    }

    @Test public void shouldSetTheDialogResolutionDeletionMessage() {
        final Dialog spyDialog = spy(new Dialog(null));
        final TextView mockMessage = mock(TextView.class);
        final ListView mockResolutionList = mock(ListView.class);
        final ResolutionElement mockElement = mock(ResolutionElement.class);
        final ResolutionListAdapter spyAdapter = spy(new ResolutionListAdapter(null, null, new ArrayList<ResolutionItem>(), null));

        when(spyActivity.findViewById(R.id.app_screen_resolution_list)).thenReturn(mockResolutionList);
        when(mockResolutionList.getAdapter()).thenReturn(spyAdapter);
        doReturn(mockElement).when(spyAdapter).getItem(5);
        when(mockElement.getWidth()).thenReturn(500);
        when(mockElement.getHeight()).thenReturn(600);
        when(spyDialog.findViewById(android.R.id.message)).thenReturn(mockMessage);

        ResolutionData.DELETION_INDEX.update(5);

        spyActivity.onPrepareDialog(DensityApplication.DENSITY_APP_DELETE_RESOLUTION_DIALOG, spyDialog);

        verify(spyDialog).findViewById(android.R.id.message);
        verify(spyActivity, times(3)).findViewById(R.id.app_screen_resolution_list);
        verify(mockMessage).setText("Delete Resolution 500x600 ?");
    }

    @Test public void shouldNotSetTheDialogResolutionDeletionMessageIfTheMessageCantBeFound() {
        final Dialog spyDialog = spy(new Dialog(null));
        when(spyDialog.findViewById(android.R.id.message)).thenReturn(null);

        ResolutionData.DELETION_INDEX.update(5);

        spyActivity.onPrepareDialog(DensityApplication.DENSITY_APP_DELETE_RESOLUTION_DIALOG, spyDialog);

        verify(spyDialog).findViewById(android.R.id.message);
        verify(spyActivity, times(2)).findViewById(R.id.app_screen_resolution_list);
    }

    @Test public void shouldShowDeletionDialogUnlessRequested() {
        final Dialog spyDialog = spy(new Dialog(null));

        ResolutionData.DELETION_INDEX.update(5);

        spyActivity.onPrepareDialog(DensityApplication.DENSITY_APP_DELETE_RESOLUTION_DIALOG + 1, spyDialog);

        verify(spyDialog, never()).findViewById(android.R.id.message);
        verify(spyActivity, times(2)).findViewById(R.id.app_screen_resolution_list);
    }
}

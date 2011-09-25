/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionItem;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class OnYesDeleteCustomResolutionDialogListenerTest {

    private static final int DELETION_POSITION = 5;
    private static final int LIST_SIZE_AFTER_DELETE = 11;
    private static final int INVALID_INDEX = -9999;

    private OnYesDeleteCustomResolutionDialogListener listener;
    private int oldDeletionIndex;
    private ResolutionListAdapter spyAdapter;
    private OnDeletionSelectedPositionFinder mockFinder;
    private ListViewDelegate mockResolutionList;

    @Before public void setup() {
        oldDeletionIndex = ResolutionData.DELETION_INDEX.getValue();
        ResolutionData.DELETION_INDEX.update(DELETION_POSITION);
        spyAdapter = spy(new ResolutionListAdapter(null, null, new ArrayList<ResolutionItem>(), null));
        mockFinder = mock(OnDeletionSelectedPositionFinder.class);
        mockResolutionList = mock(ListViewDelegate.class);

        listener = new OnYesDeleteCustomResolutionDialogListener(mockResolutionList, spyAdapter, mockFinder);
    }

    @After public void teardown() {
        ResolutionData.DELETION_INDEX.update(oldDeletionIndex);
    }

    @Test public void shouldImplementOnClickListener() {
        assertTrue("Listener should implement DialogInterface.OnClickListener",
                DialogInterface.OnClickListener.class.isAssignableFrom(OnYesDeleteCustomResolutionDialogListener.class));

    }

    @Test public void shouldDeleteTheSelectedItem() {
        final DialogInterface mockDialogInterface = mock(DialogInterface.class);

        final int positiveButton = AlertDialog.BUTTON_POSITIVE;

        when(mockFinder.findSelectionPosition(LIST_SIZE_AFTER_DELETE, DELETION_POSITION)).thenReturn(createFinderSelection(DELETION_POSITION));
        when(spyAdapter.getCount()).thenReturn(LIST_SIZE_AFTER_DELETE);
        doNothing().when(spyAdapter).removeItem(DELETION_POSITION);
        doNothing().when(spyAdapter).resetState();

        listener.onClick(mockDialogInterface, positiveButton);
        assertTrue("Should be invalid", ResolutionData.DELETION_INDEX.isInvalid());

        final InOrder inOrder = inOrder(spyAdapter, mockResolutionList, mockDialogInterface);

        inOrder.verify(spyAdapter).removeItem(DELETION_POSITION);
        inOrder.verify(spyAdapter).resetState();
        inOrder.verify(mockResolutionList).performItemClick(DELETION_POSITION, DELETION_POSITION);
        inOrder.verify(mockDialogInterface).dismiss();
    }

    private OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition createFinderSelection(final int deletionPosition) {
        return new OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition(deletionPosition, INVALID_INDEX);
    }
}

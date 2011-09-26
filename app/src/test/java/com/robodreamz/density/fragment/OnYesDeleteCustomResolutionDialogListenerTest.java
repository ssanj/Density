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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class OnYesDeleteCustomResolutionDialogListenerTest {

    private static final int DELETION_POSITION = 5;
    private static final int LIST_SIZE_AFTER_DELETE = 11;
    private static final int INVALID_INDEX = -9999;
    private static final int SELECTED_INDEX = 4;

    private OnYesDeleteCustomResolutionDialogListener listener;
    private int oldDeletionIndex;
    private ResolutionListAdapter spyAdapter;
    private OnDeletionSelectedPositionFinder mockFinder;
    private ListViewDelegate mockResolutionList;
    private DialogInterface mockDialogInterface;

    @Before public void setup() {
        oldDeletionIndex = ResolutionData.DELETION_INDEX.getValue();
        ResolutionData.DELETION_INDEX.update(DELETION_POSITION);

        ResolutionData.INDEX_PAIR.update(SELECTED_INDEX);
        spyAdapter = spy(new ResolutionListAdapter(null, null, new ArrayList<ResolutionItem>(), null));
        mockFinder = mock(OnDeletionSelectedPositionFinder.class);
        mockResolutionList = mock(ListViewDelegate.class);
        mockDialogInterface = mock(DialogInterface.class);
        listener = new OnYesDeleteCustomResolutionDialogListener(mockResolutionList, spyAdapter, mockFinder);
    }

    @After public void teardown() {
        ResolutionData.DELETION_INDEX.update(oldDeletionIndex);
        ResolutionData.INDEX_PAIR.reset();
    }

    @Test public void shouldImplementOnClickListener() {
        assertTrue("Listener should implement DialogInterface.OnClickListener",
                DialogInterface.OnClickListener.class.isAssignableFrom(OnYesDeleteCustomResolutionDialogListener.class));

    }

    @Test public void shouldDeleteTheSelectedItem() {
        final int positiveButton = AlertDialog.BUTTON_POSITIVE;
        when(mockFinder.findSelectionPosition(LIST_SIZE_AFTER_DELETE, DELETION_POSITION)).thenReturn(createFinderSelection(DELETION_POSITION));
        when(spyAdapter.getCount()).thenReturn(LIST_SIZE_AFTER_DELETE);
        doNothing().when(spyAdapter).removeItem(DELETION_POSITION);
        doNothing().when(spyAdapter).resetState();

        listener.onClick(mockDialogInterface, positiveButton);
        assertEquals("Incorrect current selected index", SELECTED_INDEX, ResolutionData.INDEX_PAIR.getCurrentIndex());

        final InOrder inOrder = inOrder(spyAdapter, mockResolutionList, mockDialogInterface);

        inOrder.verify(spyAdapter).removeItem(DELETION_POSITION);
        inOrder.verify(spyAdapter).resetState();
        inOrder.verify(mockResolutionList).performItemClick(DELETION_POSITION, DELETION_POSITION);
        inOrder.verify(mockDialogInterface).dismiss();
    }

    @Test public void shouldDeleteTheSelectedItemIfTheNextSelectionPositionIsInvalid() {
        final int positiveButton = AlertDialog.BUTTON_POSITIVE;
        when(mockFinder.findSelectionPosition(LIST_SIZE_AFTER_DELETE, DELETION_POSITION)).thenReturn(createInvalidFinderSelection());
        when(spyAdapter.getCount()).thenReturn(LIST_SIZE_AFTER_DELETE);
        doNothing().when(spyAdapter).removeItem(DELETION_POSITION);
        doNothing().when(spyAdapter).resetState();

        listener.onClick(mockDialogInterface, positiveButton);
        assertTrue("Should be invalid", ResolutionData.INDEX_PAIR.isInvalid());

        final InOrder inOrder = inOrder(spyAdapter, mockDialogInterface);

        inOrder.verify(spyAdapter).removeItem(DELETION_POSITION);
        inOrder.verify(spyAdapter).resetState();
        inOrder.verify(mockDialogInterface).dismiss();
        verifyZeroInteractions(mockResolutionList);
    }

    @Test public void shouldNotDeleteTheSelectedItemIfTheDeletionIndexIsInvalid() {
        ResolutionData.DELETION_INDEX.reset();

        listener.onClick(mockDialogInterface, -1);
        assertTrue("Should be invalid", ResolutionData.DELETION_INDEX.isInvalid());
        assertEquals("Incorrect current selected index", SELECTED_INDEX, ResolutionData.INDEX_PAIR.getCurrentIndex());

        assertTrue("Should be invalid", ResolutionData.DELETION_INDEX.isInvalid());
        verify(mockDialogInterface).dismiss();
        verifyZeroInteractions(mockResolutionList, spyAdapter);
    }

    private OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition createFinderSelection(final int deletionPosition) {
        return new OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition(deletionPosition, INVALID_INDEX);
    }

    private OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition createInvalidFinderSelection() {
        return new OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition(INVALID_INDEX);
    }
}

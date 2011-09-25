/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.content.DialogInterface;
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionItem;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(RobolectricTestRunner.class)
public final class CancelDeleteCustomResolutionDialogListenerTest {

    private static final int POSITION = 10;

    private CancelDeleteCustomResolutionDialogListener listener;
    private ResolutionListAdapter spyAdapter;
    private LayoutInflaterDelegate mockInflater;
    private DelegateFactory mockFactory;
    private Constants mockConstants;
    private List<ResolutionItem> resolutionItems;
    private int oldDeletionIndex;

    @Before public void setup() {
        mockInflater = mock(LayoutInflaterDelegate.class);
        mockFactory = mock(DelegateFactory.class);
        mockConstants = mock(Constants.class);
        resolutionItems = new ArrayList<ResolutionItem>();
        spyAdapter = spy(new ResolutionListAdapter(mockInflater, mockFactory, resolutionItems, mockConstants));
        listener = new CancelDeleteCustomResolutionDialogListener(spyAdapter);
        oldDeletionIndex = ResolutionData.DELETION_INDEX.getValue();
        ResolutionData.DELETION_INDEX.update(POSITION);
    }

    @After public void teardown() {
        ResolutionData.DELETION_INDEX.update(oldDeletionIndex);
    }

    @Test public void shouldImplementOnClickListener() {
        assertTrue("Listener should implement DialogInterface.OnClickListener",
                DialogInterface.OnClickListener.class.isAssignableFrom(CancelDeleteCustomResolutionDialogListener.class));
    }

    @Test public void shouldUnmarkItemWhenDialogIsCancelled() {
        final DialogInterface mockDialog = mock(DialogInterface.class);
        final int button = 5;
        doNothing().when(spyAdapter).unmarkForDeletion(POSITION);

        listener.onClick(mockDialog, button);

        verify(spyAdapter).unmarkForDeletion(POSITION);
        verify(mockDialog).dismiss();
        verifyZeroInteractions(mockInflater, mockFactory, mockConstants);
    }
}

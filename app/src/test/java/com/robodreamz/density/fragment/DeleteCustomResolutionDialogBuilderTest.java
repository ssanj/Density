/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Button;
import com.robodreamz.density.R;
import com.robodreamz.density.resolution.ResolutionElement;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowAlertDialog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.xtremelabs.robolectric.Robolectric.shadowOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public final class DeleteCustomResolutionDialogBuilderTest {

    private DeleteCustomResolutionDialogBuilder builder;
    private ResolutionElement mockResolutionItem;
    private Activity context;

    @Before public void setup() {
        mockResolutionItem = mock(ResolutionElement.class);
        context = new Activity();
        builder = new DeleteCustomResolutionDialogBuilder(context, mockResolutionItem);
    }

    @Test public void shouldBuildDeleteCustomResolutionDialog() {
        when(mockResolutionItem.getWidth()).thenReturn(320);
        when(mockResolutionItem.getHeight()).thenReturn(480);

        final AlertDialog dlg = builder.create();
        final ShadowAlertDialog shadowDlg = shadowOf(dlg);
        assertEquals("Incorrect title", shadowDlg.getTitle(), context.getString(R.string.delete_resolution_title));
        assertEquals("Incorrect message", "Delete Resolution 320x480 ?", shadowDlg.getMessage());
        assertEquals("Incorrect message", "Delete Resolution 320x480 ?", shadowDlg.getMessage());

        final Button positiveButton = shadowDlg.getButton(AlertDialog.BUTTON_POSITIVE);
        assertNotNull("Positive button not found", positiveButton);
        assertEquals("Incorrect positive title returned", context.getString(R.string.yes_button_title), positiveButton.getText());

        final Button negativeButton = shadowDlg.getButton(AlertDialog.BUTTON_NEGATIVE);
        assertNotNull("Negative button not found", negativeButton);
        assertEquals("Incorrect negative title returned", context.getString(R.string.no_button_title), negativeButton.getText());

        verify(mockResolutionItem).getWidth();
        verify(mockResolutionItem).getHeight();
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.view.View;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.delegate.ViewDelegate;
import org.hamcrest.core.IsSame;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsSame.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class ResolutionElementTest {

    private ResolutionElement element;

    @Before public void setup() throws Exception {
        element = new ResolutionElement(100, 200);
    }

    @Test public void shouldBeEnabled() throws Exception {
        assertTrue("Element should be enabled.", element.isEnabled());
    }

    @Test public void shouldReturnANewViewWhenViewIsNull() throws Exception {
        final LayoutInflaterDelegate mockLayoutInflaterDelegate = mock(LayoutInflaterDelegate.class);
        final ViewDelegate mockViewDelegateInput = mock(ViewDelegate.class);
        final ViewDelegate mockViewDelegateOutput = mock(ViewDelegate.class);
        final TextViewDelegate mockWidthText = mock(TextViewDelegate.class);
        final TextViewDelegate mockHeightText = mock(TextViewDelegate.class);

        when(mockViewDelegateInput.isNull()).thenReturn(true);
        when(mockLayoutInflaterDelegate.inflate(R.layout.resolution_list_view)).thenReturn(mockViewDelegateOutput);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_width)).thenReturn(mockWidthText);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_height)).thenReturn(mockHeightText);

        assertThat("Incorrect ViewDelegate instance returned",
                element.getView(mockLayoutInflaterDelegate, mockViewDelegateInput), sameInstance(mockViewDelegateOutput));

                verify(mockViewDelegateOutput).setTag(ResolutionItem.ViewType.ELEMENT);
        verify(mockWidthText).setText("100");
        verify(mockHeightText).setText("200");
    }

    @Test public void shouldReturnANewViewWhenViewIsOfTheWrongTag() throws Exception {
        final LayoutInflaterDelegate mockLayoutInflaterDelegate = mock(LayoutInflaterDelegate.class);
        final ViewDelegate mockViewDelegateInput = mock(ViewDelegate.class);
        final ViewDelegate mockViewDelegateOutput = mock(ViewDelegate.class);
        final TextViewDelegate mockWidthText = mock(TextViewDelegate.class);
        final TextViewDelegate mockHeightText = mock(TextViewDelegate.class);

        when(mockViewDelegateInput.isNull()).thenReturn(false);
        when(mockViewDelegateInput.hasTag(ResolutionItem.ViewType.ELEMENT)).thenReturn(false);
        when(mockLayoutInflaterDelegate.inflate(R.layout.resolution_list_view)).thenReturn(mockViewDelegateOutput);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_width)).thenReturn(mockWidthText);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_height)).thenReturn(mockHeightText);

        assertThat("Incorrect ViewDelegate instance returned",
                element.getView(mockLayoutInflaterDelegate, mockViewDelegateInput), sameInstance(mockViewDelegateOutput));

        verify(mockViewDelegateOutput).setTag(ResolutionItem.ViewType.ELEMENT);
        verify(mockWidthText).setText("100");
        verify(mockHeightText).setText("200");
    }

    @Test public void shouldReuseViewIfViewIsNotNullAndOfCorrectTag() {
        final LayoutInflaterDelegate mockLayoutInflaterDelegate = mock(LayoutInflaterDelegate.class);
        final ViewDelegate mockViewDelegateInput = mock(ViewDelegate.class);
        final TextViewDelegate mockWidthText = mock(TextViewDelegate.class);
        final TextViewDelegate mockHeightText = mock(TextViewDelegate.class);

        when(mockViewDelegateInput.isNull()).thenReturn(false);
        when(mockViewDelegateInput.hasTag(ResolutionItem.ViewType.ELEMENT)).thenReturn(true);

        when(mockViewDelegateInput.findViewById(R.id.resolution_list_view_width)).thenReturn(mockWidthText);
        when(mockViewDelegateInput.findViewById(R.id.resolution_list_view_height)).thenReturn(mockHeightText);

        assertThat("Incorrect ViewDelegate instance returned",
                element.getView(mockLayoutInflaterDelegate, mockViewDelegateInput), sameInstance(mockViewDelegateInput));

        verify(mockWidthText).setText("100");
        verify(mockHeightText).setText("200");
    }
}

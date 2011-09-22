/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.graphics.Color;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.delegate.ViewDelegate;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class StandardResolutionElementTest {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 200;

    private ResolutionElement element;

    @Before public void setup() throws Exception {
        element = new StandardResolutionElement(WIDTH, HEIGHT);
    }

    @Test public void shouldImplementResolutionElemen() {
        assertTrue("StandardResolutionElement does not implements ResolutionElement",
                ResolutionElement.class.isAssignableFrom(StandardResolutionElement.class));
    }

    @Test public void shouldReturnWidth() {
        assertEquals("Incorrect Width returned", WIDTH, element.getWidth());
    }

    @Test public void shouldReturnHeight() {
        assertEquals("Incorrect Height returned", HEIGHT, element.getHeight());
    }

    @Test public void shouldBeEnabled() throws Exception {
        assertTrue("Element should be enabled.", element.isEnabled());
    }

    @Test public void shouldReturnANewViewWhenViewIsNulAndElementIsNotChecked() throws Exception {
        assertReturnANewViewWhenViewIsNull(Color.BLACK);
    }

    @Test public void shouldReturnANewViewWhenViewIsNullAndElementIsChecked() throws Exception {
        element.check();
        assertReturnANewViewWhenViewIsNull(Color.GREEN);
    }

    @Test public void shouldReturnANewViewWhenViewIsOfTheWrongTagAndNotChecked() throws Exception {
        assertReturnANewViewWhenViewIsOfTheWrongTag(Color.BLACK);
    }

    @Test public void shouldReturnANewViewWhenViewIsOfTheWrongTagAndIsChecked() throws Exception {
        element.check();
        assertReturnANewViewWhenViewIsOfTheWrongTag(Color.GREEN);
    }

    @Test public void shouldReuseViewIfViewIsNotNullAndOfCorrectTagAndIsNotChecked() {
        assertReuseViewIfViewIsNotNullAndOfCorrectTag(Color.BLACK);
    }

    @Test public void shouldReuseViewIfViewIsNotNullAndOfCorrectTagAndIsChecked() {
        element.check();
        assertReuseViewIfViewIsNotNullAndOfCorrectTag(Color.GREEN);
    }

    private void assertReuseViewIfViewIsNotNullAndOfCorrectTag(final int background) {
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
        verify(mockViewDelegateInput).setBackground(background);
    }

    private void assertReturnANewViewWhenViewIsOfTheWrongTag(final int background) {
        final LayoutInflaterDelegate mockLayoutInflaterDelegate = mock(LayoutInflaterDelegate.class);
        final ViewDelegate mockViewDelegateInput = mock(ViewDelegate.class);
        final ViewDelegate mockViewDelegateOutput = mock(ViewDelegate.class);
        final TextViewDelegate mockWidthText = mock(TextViewDelegate.class);
        final TextViewDelegate mockHeightText = mock(TextViewDelegate.class);

        when(mockViewDelegateInput.isNull()).thenReturn(false);
        when(mockViewDelegateInput.hasTag(ResolutionItem.ViewType.ELEMENT)).thenReturn(false);
        when(mockLayoutInflaterDelegate.inflate(R.layout.resolution_list_standard_view)).thenReturn(mockViewDelegateOutput);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_width)).thenReturn(mockWidthText);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_height)).thenReturn(mockHeightText);

        assertThat("Incorrect ViewDelegate instance returned",
                element.getView(mockLayoutInflaterDelegate, mockViewDelegateInput), sameInstance(mockViewDelegateOutput));

        verify(mockViewDelegateOutput).setTag(ResolutionItem.ViewType.ELEMENT);
        verify(mockWidthText).setText("100");
        verify(mockHeightText).setText("200");
        verify(mockViewDelegateOutput).setBackground(background);
    }

    private void assertReturnANewViewWhenViewIsNull(final int background) {
        final LayoutInflaterDelegate mockLayoutInflaterDelegate = mock(LayoutInflaterDelegate.class);
        final ViewDelegate mockViewDelegateInput = mock(ViewDelegate.class);
        final ViewDelegate mockViewDelegateOutput = mock(ViewDelegate.class);
        final TextViewDelegate mockWidthText = mock(TextViewDelegate.class);
        final TextViewDelegate mockHeightText = mock(TextViewDelegate.class);

        when(mockViewDelegateInput.isNull()).thenReturn(true);
        when(mockLayoutInflaterDelegate.inflate(R.layout.resolution_list_standard_view)).thenReturn(mockViewDelegateOutput);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_width)).thenReturn(mockWidthText);
        when(mockViewDelegateOutput.findViewById(R.id.resolution_list_view_height)).thenReturn(mockHeightText);

        assertThat("Incorrect ViewDelegate instance returned",
                element.getView(mockLayoutInflaterDelegate, mockViewDelegateInput), sameInstance(mockViewDelegateOutput));

        verify(mockViewDelegateOutput).setTag(ResolutionItem.ViewType.ELEMENT);
        verify(mockWidthText).setText("100");
        verify(mockHeightText).setText("200");
        verify(mockViewDelegateOutput).setBackground(background);
    }

    @Test public void shouldStoreItsCheckedState() {
        assertFalse("Element should be unchecked", element.isChecked());
        element.check();
        assertTrue("Element should be checked", element.isChecked());
        element.uncheck();
        assertFalse("Element should be unchecked", element.isChecked());
    }
}

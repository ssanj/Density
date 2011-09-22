/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class CustomResolutionElementTest {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;
    private static final ResolutionItem.ViewType ELEMENT_TYPE = ResolutionItem.ViewType.CUSTOM_ELEMENT;
    private static final int LAYOUT_ID = R.layout.resolution_list_custom_view;

    private CustomResolutionElement element;
    private ResolutionElement mockResolutionElement;

    @Before public void setup() {
        mockResolutionElement = mock(ResolutionElement.class);
        element = new CustomResolutionElement(mockResolutionElement);
    }

    @After public void teardown() {
        verify(mockResolutionElement).setViewType(ELEMENT_TYPE);
        verify(mockResolutionElement).setElementLayoutId(LAYOUT_ID);
    }

    @Test public void shouldImplementResolutionElement() {
        assertTrue("CustomResolutionElement does not implement ResolutionElement",
                ResolutionElement.class.isAssignableFrom(CustomResolutionElement.class));
    }

    @Test public void shouldReturnHeight() {
        when(mockResolutionElement.getHeight()).thenReturn(HEIGHT);
        assertEquals("Incorrect height returned", HEIGHT, element.getHeight());
        verify(mockResolutionElement).getHeight();
    }

    @Test public void shouldReturnWidth() {
        when(mockResolutionElement.getWidth()).thenReturn(WIDTH);
        assertEquals("Incorrect width returned", WIDTH, element.getWidth());
        verify(mockResolutionElement).getWidth();
    }

    @Test public void shouldDelegateIsEnabled() {
        assertIsEnabled(true);
    }

    @Test public void shouldDelegateIsNotEnabled() {
        assertIsEnabled(false);
    }

    @Test public void shouldDelegateViewCreation() {
        final LayoutInflaterDelegate mockInflater = mock(LayoutInflaterDelegate.class);
        final ViewDelegate mockOldView = mock(ViewDelegate.class);
        final ViewDelegate mockNewView = mock(ViewDelegate.class);
        when(mockResolutionElement.getView(mockInflater, mockOldView)).thenReturn(mockNewView);
        assertEquals("Incorrect ViewDelegate returned", mockNewView, element.getView(mockInflater, mockOldView));
        verify(mockResolutionElement).getView(mockInflater, mockOldView);
    }

    @Test public void shouldDelegateViewType() {
        when(mockResolutionElement.getViewType()).thenReturn(ELEMENT_TYPE);
        assertEquals("Incorrect view type returned", ELEMENT_TYPE, element.getViewType());
        verify(mockResolutionElement).getViewType();
    }

    @Test public void shouldDelegateCheck() {
        element.check();
        verify(mockResolutionElement).check();
    }

    @Test public void shouldDelegateUnCheck() {
        element.uncheck();
        verify(mockResolutionElement).uncheck();
    }

    @Test public void shouldDelegateIsChecked() {
        assertChecked(true);
    }

    @Test public void shouldDelegateIsNotChecked() {
        assertChecked(false);
    }

    @Test public void shouldDelegateElementLayout() {
        when(mockResolutionElement.getElementLayoutId()).thenReturn(LAYOUT_ID);
        assertEquals("Incorrect layoutid returned", LAYOUT_ID, element.getElementLayoutId());
        verify(mockResolutionElement).getElementLayoutId();
    }

    private void assertChecked(final boolean checked) {
        when(mockResolutionElement.isChecked()).thenReturn(checked);
        assertEquals("Incorrect checked status returned", checked, element.isChecked());
        verify(mockResolutionElement).isChecked();
    }

    private void assertIsEnabled(final boolean enabled) {
        when(mockResolutionElement.isEnabled()).thenReturn(enabled);
        assertEquals("Should have been enabled", enabled, element.isEnabled());
        verify(mockResolutionElement).isEnabled();
    }
}

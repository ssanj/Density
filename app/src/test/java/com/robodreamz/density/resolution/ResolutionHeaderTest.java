/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;
import org.hamcrest.core.IsSame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class ResolutionHeaderTest {

    private ResolutionHeader header;

    @Before public void setup() throws Exception {
        header = new ResolutionHeader();
    }

    @Test public void shouldBeDisabled() throws Exception {
        assertFalse("Header should be disabled", header.isEnabled());
    }

    @Test public void shouldReturnANewViewWhenViewIsNull() throws Exception {
        final LayoutInflaterDelegate mockLayoutInflaterDelegate = mock(LayoutInflaterDelegate.class);
        final ViewDelegate mockViewDelegateInput = mock(ViewDelegate.class);
        final ViewDelegate mockViewDelegateOutput = mock(ViewDelegate.class);

        when(mockViewDelegateInput.isNull()).thenReturn(true);
        when(mockLayoutInflaterDelegate.inflate(R.layout.resolution_list_header_element)).thenReturn(mockViewDelegateOutput);

        assertThat("Incorrect ViewDelegate returned.",
                header.getView(mockLayoutInflaterDelegate, mockViewDelegateInput), IsSame.sameInstance(mockViewDelegateOutput));

        verify(mockViewDelegateOutput).setTag(ResolutionItem.ViewType.HEADER);
    }
}

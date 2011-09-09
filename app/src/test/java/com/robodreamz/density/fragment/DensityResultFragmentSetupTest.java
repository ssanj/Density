/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class DensityResultFragmentSetupTest {

    private DensityResultFragmentSetup fragmentSetup;
    private ActivityDelegate mockActivity;

    @Before public void setup() {
        mockActivity = mock(ActivityDelegate.class);
        fragmentSetup = new DensityResultFragmentSetup(mockActivity);
    }

    @Test public void shouldInitializeDensityValues() {
        final TextViewDelegate mockValue = mock(TextViewDelegate.class);
        final TextViewDelegate mockCategory = mock(TextViewDelegate.class);

        when(mockActivity.findViewById(R.id.density_result_density_value_text)).thenReturn(mockValue);
        when(mockActivity.findViewById(R.id.density_result_density_value_category)).thenReturn(mockCategory);

        fragmentSetup.setup();

        verify(mockValue).setText("0");
        verify(mockCategory).setText("NODPI");
    }
}

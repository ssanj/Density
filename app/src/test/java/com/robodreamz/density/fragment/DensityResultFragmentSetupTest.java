/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.screen.DefaultDensity;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class DensityResultFragmentSetupTest {

    private DensityResultFragmentSetup fragmentSetup;
    private DefaultDensity mockDensity;


    @Before public void setup() {
        mockDensity = mock(DefaultDensity.class);
        fragmentSetup = new DensityResultFragmentSetup(mockDensity);
    }

    @Test public void shouldInitializeDensityValues() {
        fragmentSetup.setup();
        verify(mockDensity).setValue();
    }
}

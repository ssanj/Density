/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

import com.robodreamz.density.calc.DensityCalculator;
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class DensityResultCalculatorSynchronizingWithListTest {

    private ActivityDelegate mockActivity;
    private DensityCalculator mockCalculator;
    private ScreenSizeResolver mockResolver;
    private DensitySifter mockSifter;
    private DensityResultCalculator calcSpy;
    private Constants mockConstants;
    private int oldIndex;

    @Before public void setup() throws Exception {
        mockActivity = mock(ActivityDelegate.class);
        mockCalculator = mock(DensityCalculator.class);
        mockResolver = mock(ScreenSizeResolver.class);
        mockSifter = mock(DensitySifter.class);
        mockConstants = mock(Constants.class);

        oldIndex = ResolutionData.INDEX_PAIR.getCurrentIndex();

        calcSpy = spy(new DensityResultCalculator(mockActivity, mockCalculator, mockResolver, mockSifter, mockConstants));
    }

    @Test public void shouldSynchronizeWithSuppliedListIfThereIsASelectedRow() {
        ResolutionData.INDEX_PAIR.update(2);
        final ListViewDelegate mockList = mock(ListViewDelegate.class);

        when(mockConstants.isInvalidPosition(2)).thenReturn(false);
        doNothing().when(calcSpy).calculateDensity(2, mockList);

        calcSpy.calculateDensity(mockList);
        verify(calcSpy).calculateDensity(2, mockList);
    }

    @Test public void shouldNotSynchronizeWithSuppliedListIfThereIsNoSelectedRow() {
        ResolutionData.INDEX_PAIR.update(1);
        final ListViewDelegate mockList = mock(ListViewDelegate.class);
        when(mockConstants.isInvalidPosition(1)).thenReturn(true);

        calcSpy.calculateDensity(mockList);
        verify(calcSpy, never()).calculateDensity(1, mockList);
    }

    @After public void teardown() {
        ResolutionData.INDEX_PAIR.update(oldIndex);
    }
}

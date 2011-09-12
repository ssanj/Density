/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.delegate.SeekBarDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.screen.DensityResultCalculator;
import com.robodreamz.density.screen.ScreenSizeResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public final class ScreenSizeFragmentSetupTest {

    private ScreenSizeFragmentSetup fragmentSetup;
    private ActivityDelegate mockActivity;
    private DelegateFactory mockDelegateFactory;
    private TextViewDelegate mockScreenSizeValue;
    private SeekBarDelegate mockIntegralBar;
    private SeekBarDelegate mockDecimalBar;
    private DensityResultCalculator mockCalc;
    private ListViewDelegate mockResolutionList;
    private ScreenSizeResolver mockResolver;

    @Before public void setup() {
        mockActivity = mock(ActivityDelegate.class);
        mockDelegateFactory = mock(DelegateFactory.class);

        mockScreenSizeValue = mock(TextViewDelegate.class);
        mockIntegralBar = mock(SeekBarDelegate.class);
        mockDecimalBar = mock(SeekBarDelegate.class);
        mockResolutionList = mock(ListViewDelegate.class);
        mockCalc = mock(DensityResultCalculator.class);
        mockResolver = mock(ScreenSizeResolver.class);
        fragmentSetup = new ScreenSizeFragmentSetup(mockActivity, mockDelegateFactory);
    }

    @Test public void shouldSetupInitialValues() throws Exception {
        when(mockActivity.findViewById(R.id.app_screen_screensize_value_text)).thenReturn(mockScreenSizeValue);
        when(mockActivity.findViewById(R.id.app_screen_screensize_integer_progress)).thenReturn(mockIntegralBar);
        when(mockActivity.findViewById(R.id.app_screen_screensize_decimal_progress)).thenReturn(mockDecimalBar);
        when(mockActivity.findViewById(R.id.app_screen_resolution_list)).thenReturn(mockResolutionList);

        fragmentSetup.setup(mockCalc);

        verify(mockIntegralBar).setOnSeekBarChangeListener(isA(ScreenSizeFragmentSetup.IntegralBarChangeListener.class));
        verify(mockDecimalBar).setOnSeekBarChangeListener(isA(ScreenSizeFragmentSetup.DecimalBarChangeListener.class));
        verify(mockIntegralBar).setProgress(0);
        verify(mockDecimalBar).setProgress(5);
    }

    @Test public void shouldUpdateScreenSizeWhenIntegralSeekBarIsMoved() {
        final String densityResult = "2.5";

        final ScreenSizeFragmentSetup.IntegralBarChangeListener listener = new ScreenSizeFragmentSetup.IntegralBarChangeListener(mockDecimalBar,
                mockScreenSizeValue, mockCalc, mockResolutionList, mockDelegateFactory);

        when(mockDecimalBar.getProgress()).thenReturn(5);
        when(mockResolver.convertProgressValueToActualString(2, 5)).thenReturn(densityResult);

        listener.onProgressChanged(mockResolver, mockIntegralBar, 2, true);

        verify(mockScreenSizeValue).setText(densityResult);
        verify(mockDecimalBar).getProgress();
        verify(mockCalc).calculateDensity(mockResolutionList);
        verifyZeroInteractions(mockIntegralBar);
    }

    @Test public void shouldUpdateScreenSizeWhenDecimalSeekBarIsMoved() {
        final String densityResult = "10.2";

        final ScreenSizeFragmentSetup.DecimalBarChangeListener listener = new ScreenSizeFragmentSetup.DecimalBarChangeListener(mockIntegralBar,
                mockScreenSizeValue, mockCalc, mockResolutionList, mockDelegateFactory);

        when(mockIntegralBar.getProgress()).thenReturn(1);
        when(mockResolver.convertProgressValueToActualString(1, 3)).thenReturn(densityResult);

        listener.onProgressChanged(mockResolver, mockDecimalBar, 3, true);

        verify(mockScreenSizeValue).setText(densityResult);
        verify(mockIntegralBar).getProgress();
        verify(mockCalc).calculateDensity(mockResolutionList);
        verifyZeroInteractions(mockDecimalBar);
    }
}

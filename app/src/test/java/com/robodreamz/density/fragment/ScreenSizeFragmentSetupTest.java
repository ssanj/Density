/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.SeekBarDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
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

    @Before public void setup() {
        mockActivity = mock(ActivityDelegate.class);
        mockDelegateFactory = mock(DelegateFactory.class);
        fragmentSetup = new ScreenSizeFragmentSetup(mockActivity, mockDelegateFactory);
    }

    @Test public void shouldSetupInitialValues() throws Exception {
        final TextViewDelegate mockScreenSizeValue = mock(TextViewDelegate.class);
        final SeekBarDelegate mockIntegralBar = mock(SeekBarDelegate.class);
        final SeekBarDelegate mockDecimalBar = mock(SeekBarDelegate.class);

        when(mockActivity.findViewById(R.id.app_screen_screensize_value_text)).thenReturn(mockScreenSizeValue);
        when(mockActivity.findViewById(R.id.app_screen_screensize_integer_progress)).thenReturn(mockIntegralBar);
        when(mockActivity.findViewById(R.id.app_screen_screensize_decimal_progress)).thenReturn(mockDecimalBar);

        fragmentSetup.setup();

        verify(mockIntegralBar).setOnSeekBarChangeListener(isA(ScreenSizeFragmentSetup.IntegralBarChangeListener.class));
        verify(mockDecimalBar).setOnSeekBarChangeListener(isA(ScreenSizeFragmentSetup.DecimalBarChangeListener.class));
        verify(mockIntegralBar).setProgress(0);
        verify(mockDecimalBar).setProgress(5);
    }

    @Test public void shouldUpdateScreenSizeWhenIntegralSeekBarIsMoved() {
        final SeekBarDelegate mockIntegralBar = mock(SeekBarDelegate.class);
        final SeekBarDelegate mockDecimalBar = mock(SeekBarDelegate.class);
        final TextViewDelegate mockScreenSize = mock(TextViewDelegate.class);

        final ScreenSizeFragmentSetup.IntegralBarChangeListener listener = new ScreenSizeFragmentSetup.IntegralBarChangeListener(mockDecimalBar,
                mockScreenSize, mockDelegateFactory);

        when(mockDecimalBar.getProgress()).thenReturn(5);
        listener.onProgressChanged(mockIntegralBar, 2, true);

        verify(mockScreenSize).setText("4.5");
        verify(mockDecimalBar).getProgress();
        verifyZeroInteractions(mockIntegralBar);
    }

    @Test public void shouldUpdateScreenSizeWhenDecimalSeekBarIsMoved() {
        final SeekBarDelegate mockIntegralBar = mock(SeekBarDelegate.class);
        final SeekBarDelegate mockDecimalBar = mock(SeekBarDelegate.class);
        final TextViewDelegate mockScreenSize = mock(TextViewDelegate.class);
        final ScreenSizeFragmentSetup.DecimalBarChangeListener listener = new ScreenSizeFragmentSetup.DecimalBarChangeListener(mockIntegralBar,
                mockScreenSize, mockDelegateFactory);

        when(mockIntegralBar.getProgress()).thenReturn(1);
        listener.onProgressChanged(mockDecimalBar, 3, true);

        verify(mockScreenSize).setText("3.3");
        verify(mockIntegralBar).getProgress();
        verifyZeroInteractions(mockDecimalBar);
    }
}

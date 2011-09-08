/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.SeekBarDelegate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;

public final class ScreenSizeResolverWithGetDiagonalTest {

    private ScreenSizeResolver resolver;

    @Before public void setup() {
        resolver = new ScreenSizeResolver();
    }

    @Test public void shouldReturnTheCurrentDiagonalScreenSizeSelected() {
        final ActivityDelegate mockActivity = mock(ActivityDelegate.class);
        final SeekBarDelegate mockIntegralBar = mock(SeekBarDelegate.class);
        final SeekBarDelegate mockDecimalBarecimalBar = mock(SeekBarDelegate.class);

        when(mockActivity.findViewById(R.id.app_screen_screensize_integer_progress)).thenReturn(mockIntegralBar);
        when(mockActivity.findViewById(R.id.app_screen_screensize_decimal_progress)).thenReturn(mockDecimalBarecimalBar);
        when(mockIntegralBar.getProgress()).thenReturn(1);
        when(mockDecimalBarecimalBar.getProgress()).thenReturn(2);

        assertEquals("Incorrect screen diagonal size returned", 3.2d, resolver.getScreenDiagonal(mockActivity), 0.1d);
    }

    @Test public void shouldReturnZeroIfTheScreenSizeReturnedIsInvalid() {
        final ScreenSizeResolver spied = spy(resolver);

        final ActivityDelegate mockActivity = mock(ActivityDelegate.class);
        final SeekBarDelegate mockIntegralBar = mock(SeekBarDelegate.class);
        final SeekBarDelegate mockDecimalBarecimalBar = mock(SeekBarDelegate.class);

        when(mockActivity.findViewById(R.id.app_screen_screensize_integer_progress)).thenReturn(mockIntegralBar);
        when(mockActivity.findViewById(R.id.app_screen_screensize_decimal_progress)).thenReturn(mockDecimalBarecimalBar);
        when(mockIntegralBar.getProgress()).thenReturn(0);
        when(mockDecimalBarecimalBar.getProgress()).thenReturn(0);
        stub(spied.convertProgressValueToActualString(0, 0)).toReturn("ABC");

        assertEquals("Incorrect screen diagonal size returned", 0d, spied.getScreenDiagonal(mockActivity), 0d);
    }
}

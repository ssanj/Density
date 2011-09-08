/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.SeekBarDelegate;

public class ScreenSizeResolver {

    public static final int INTEGRAL_SCREENSIZE_OFFSET = 2;

    public CharSequence convertProgressValueToActualString(final int integral, final int decimal) {
        return new StringBuilder(String.valueOf(INTEGRAL_SCREENSIZE_OFFSET + integral)).append('.').append(decimal);
    }

    public int convertProgressValueToActualInt(final int integral) {
        return integral + INTEGRAL_SCREENSIZE_OFFSET;
    }

    public int convertActualValueToProgressInt(final int progressValue) {
        return progressValue - INTEGRAL_SCREENSIZE_OFFSET;
    }

    public double getScreenDiagonal(ActivityDelegate delegate) {
        final SeekBarDelegate integerBar = (SeekBarDelegate) delegate.findViewById(R.id.app_screen_screensize_integer_progress);
        final SeekBarDelegate decimalBar = (SeekBarDelegate) delegate.findViewById(R.id.app_screen_screensize_decimal_progress);
        final CharSequence screenDiagonal = convertProgressValueToActualString(integerBar.getProgress(), decimalBar.getProgress());
        try {
            return Double.parseDouble(screenDiagonal.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

public final class ScreenSizeResolver {

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
}

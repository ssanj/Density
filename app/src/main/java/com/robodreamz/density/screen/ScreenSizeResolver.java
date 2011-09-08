/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

public final class ScreenSizeResolver {

    public static final int INTEGRAL_SCREENSIZE_OFFSET = 2;

    public CharSequence resolve(final int integral, final int decimal) {
        return new StringBuilder(String.valueOf(INTEGRAL_SCREENSIZE_OFFSET + integral)).append('.').append(decimal);
    }

}

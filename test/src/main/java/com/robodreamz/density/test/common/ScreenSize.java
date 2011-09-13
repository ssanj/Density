/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test.common;

import com.robodreamz.density.DensityApplication;
import com.robodreamz.density.screen.ScreenSizeResolver;

public final class ScreenSize {
    public final int integral;
    public final int decimal;

    public ScreenSize(final int integral, final int decimal) {
        this.integral = integral - ScreenSizeResolver.INTEGRAL_SCREENSIZE_OFFSET;
        this.decimal = decimal;
    }

    public String getScreenSize() {
        return DensityApplication.getResolver().convertProgressValueToActualString(integral, decimal).toString();
    }
}

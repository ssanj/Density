/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.calc;

public final class DensityCalculator {

    public static final int ERROR_VALUE = -9999;
    private static final int MAX_RESOLUTION_SIZE = Integer.MAX_VALUE;
    private static final double MAX_SCREEN_DIAGONAL_SIZE = 10000.0d;

    public int getDensityFor(final int width, final int height, final double screenLength) {
        if (invalidInteger(width) || invalidInteger(height) || invalidDouble(screenLength)) {
            return ERROR_VALUE;
        } else {
            return (int) Math.round(Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / screenLength);
        }
    }

    private boolean invalidDouble(final double screenLength) {
        return screenLength <= 0 || screenLength >= MAX_SCREEN_DIAGONAL_SIZE;
    }

    private boolean invalidInteger(final int size) {
        return size <= 0 || size >= MAX_RESOLUTION_SIZE;
    }
}

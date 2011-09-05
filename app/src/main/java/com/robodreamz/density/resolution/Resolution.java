/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

public final class Resolution {
    public int width;
    public int height;

    public static Resolution resolution(int width, int height) {
        final Resolution resolution = new Resolution();
        resolution.width = width;
        resolution.height = height;
        return resolution;
    }
}

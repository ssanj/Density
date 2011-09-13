/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test.common;

public final class Resolution {
    public final int width;
    public final int height;

    public Resolution(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public String getWidthAsString() {
        return String.valueOf(width);
    }

    public String getHeightAsString() {
        return String.valueOf(height);
    }

    @Override public String toString() {
        return "Resolution{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}

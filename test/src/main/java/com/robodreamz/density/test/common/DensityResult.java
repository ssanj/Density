/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test.common;

public final class DensityResult {
    public final int value;
    public final String category;

    public DensityResult(final int value, final String category) {
        this.value = value;
        this.category = category;
    }

    public String getValueAsString() {
        return String.valueOf(value);
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.calc;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public final class DensitySifterTest {

    private DensitySifter sifter;

    @Before public void setup() {
        sifter = new DensitySifter();
    }

    @Test public void shouldSiftLdpiDensities() {
        assertdpi(DensitySifter.DPI.LDPI);
    }

    @Test public void shouldSiftMdpiDensities() {
        assertdpi(DensitySifter.DPI.MDPI);
    }

    @Test public void shouldSiftHdpiDensities() {
        assertdpi(DensitySifter.DPI.HDPI);
    }

    @Test public void shouldSiftXhdpiDensities() {
        assertdpi(DensitySifter.DPI.XHDPI);
    }

    @Test public void shouldSiftNodpiDensities() {
        assertNodpi(-1);
        assertNodpi(3201);
        assertNodpi(Integer.MIN_VALUE);
        assertNodpi(Integer.MAX_VALUE);
    }

    private void assertNodpi(final int dpi) {
        assertThat("Incorrect dpi value", sifter.sift(dpi), equalTo(DensitySifter.DPI.NODPI));
    }

    private void assertdpi(final DensitySifter.DPI dpi) {
        final int max = dpi.getMax();
        final int min = dpi.getMin();

        for (int count = min; count <= max; count++) {
            assertThat("Incorrect dpi value: " + count, sifter.sift(count), equalTo(dpi));
        }
    }
}

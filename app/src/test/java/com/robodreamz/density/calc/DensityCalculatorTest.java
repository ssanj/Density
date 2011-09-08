/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.calc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class DensityCalculatorTest {

    private DensityCalculator calc;

    @Before public void setup() throws Exception {
        calc = new DensityCalculator();
    }

    @Test public void shouldCalculateTheDensityGivenAWidthAAHeightAndAScreenDiagonalLength() {
        assertEquals("Incorrect density value", 102, calc.getDensityFor(100, 200, 2.2));
    }

    @Test public void shouldCalculateTheDensityGivenAnotherWidthAAHeightAndAScreenDiagonalLength() {
        assertEquals("Incorrect density value", 125, calc.getDensityFor(320, 240, 3.2));
    }

    @Test public void shouldReturnAnErrorneousValueforZero() {
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(0, 240, 3.2));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(320, 0, 3.2));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(320, 240, 0));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(0, 0, 0));
    }

    @Test public void shouldReturnErrorneousValueForNegativeNumbers() {
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(-1, 240, 3.2));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(320, -2, 3.2));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(320, 240, -3.2));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(-3, -1, -0));
    }

    @Test public void shouldReturnErrorneousValueForMaxRanges() {
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(Integer.MAX_VALUE, 240, 3.2));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(320, Integer.MAX_VALUE, 3.2));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(320, 240, Double.MAX_VALUE));
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, calc.getDensityFor(Integer.MAX_VALUE, Integer.MAX_VALUE,
                Double.MAX_VALUE));
    }
}

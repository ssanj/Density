/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.calc;

import org.junit.Before;
import org.junit.Test;

import static com.robodreamz.density.calc.DensityCalculator.HEIGHT;
import static com.robodreamz.density.calc.DensityCalculator.MAX_RESOLUTION_SIZE;
import static com.robodreamz.density.calc.DensityCalculator.MAX_SCREEN_DIAGONAL_SIZE;
import static com.robodreamz.density.calc.DensityCalculator.WIDTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public final class DensityCalculatorTest {

    private DensityCalculator calc;

    @Before public void setup() throws Exception {
        calc = new DensityCalculator();
    }

    @Test public void shouldCalculateTheDensityGivenAWidthAAHeightAndAScreenDiagonalLength() {
        assertValidResult(calc.getDensityFor(100, 200, 2.2), 102);
    }

    @Test public void shouldCalculateTheDensityGivenAnotherWidthAAHeightAndAScreenDiagonalLength() {
        assertValidResult(calc.getDensityFor(320, 240, 3.2), 125);
    }

    @Test public void shouldReturnAnErrorneousValueforZero() {
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(0, 240, 3.2), "Width");
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(320, 0, 3.2), "Height");
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(320, 240, 0), "Screen diagonal");
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(0, 0, 0), "Width");
    }

    @Test public void shouldReturnErrorneousValueForNegativeNumbers() {
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(-1, 240, 3.2), "Width");
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(320, -2, 3.2), "Height");
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(320, 240, -3.2), "Screen diagonal");
        assertInvalidZeroAndLessThanZeroValue(calc.getDensityFor(-3, -1, -0), "Width");
    }

    @Test public void shouldReturnErrorneousValueForMaxRanges() {
        assertInvalidMaxValue(calc.getDensityFor(Integer.MAX_VALUE, 240, 3.2), WIDTH);
        assertInvalidMaxValue(calc.getDensityFor(320, Integer.MAX_VALUE, 3.2), HEIGHT);
        assertInvalidMaxValueForScreenSize(calc.getDensityFor(320, 240, Double.MAX_VALUE));
        assertInvalidMaxValue(calc.getDensityFor(Integer.MAX_VALUE, Integer.MAX_VALUE, Double.MAX_VALUE), WIDTH);
    }

    private void assertInvalidMaxValue(final DensityCalculator.DensityCaluclation result, final String attribute) {
        assertFalse("Density result should be invalid", result.isValid());
        assertEquals("Incorrect error String", attribute + " is greater than max resolution size: " + MAX_RESOLUTION_SIZE, result.getError());
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, result.getResult());
    }

    private void assertInvalidMaxValueForScreenSize(final DensityCalculator.DensityCaluclation result) {
        assertFalse("Density result should be invalid", result.isValid());
        assertEquals("Incorrect error String", "Screen diagonal is greater than max value: " + MAX_SCREEN_DIAGONAL_SIZE, result.getError());
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, result.getResult());
    }

    //TODO: Add test for verifying the first error is the error returned.

    private void assertValidResult(final DensityCalculator.DensityCaluclation result, final int value) {
        assertNotNull("Density result is null", result);
        assertTrue("Density result should be valid", result.isValid());
        assertEquals("Density result should be valid", value, result.getResult());
    }

    private void assertInvalidZeroAndLessThanZeroValue(final DensityCalculator.DensityCaluclation result, final String attribute) {
        assertFalse("Density result should be invalid", result.isValid());
        assertEquals("Incorrect error String", attribute + " is less than 1", result.getError());
        assertEquals("Incorrect density value", DensityCalculator.ERROR_VALUE, result.getResult());
    }
}

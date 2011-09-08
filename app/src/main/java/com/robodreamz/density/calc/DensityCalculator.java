/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.calc;

/**
 * The calculations for screen density are done here. We use the following formula for calculations:
 * SQR(SQ(WIDTH) + SQ(HEIGHT)) / SCREEN_DIAGONAL_LENGTH.
 * <p/>
 * We try not to throw Exceptions or return null but instead return the results as objects that must be queried for its state.
 * <p/>
 * Always call  <code>DensityCaluclation#isValid</code> to learn the current state of the result.
 * @see  DensityCaluclation
 */
public final class DensityCalculator {

    public static final int ERROR_VALUE = -9999;
    public static final String NONE = "NONE";

    //We don't really know how big resolutions will go. Integer.MAX_VALUE is a decent guess.
    static final int MAX_RESOLUTION_SIZE = Integer.MAX_VALUE;
    //Given that we have max screen sizes of around 10-12", a x1000 should be enough.
    static final double MAX_SCREEN_DIAGONAL_SIZE = 10000.0d;

    static final String WIDTH = "Width";
    static final String HEIGHT = "Height";
    static final String SCREEN_DIAGONAL = "Screen diagonal";

    /**
         * Calculates the screen resolution given the width, height and screen length. The width and height can be transposed as this does not affect the calculated value.
         * @param width The resolution width.
         * @param height The resolution heght.
         * @param screenLength The length of the screen diagonal.
         * @return DensityCaluclation with the result. If in error, <code>DensityCaluclation#isValid</code> returns false. In this case call <code>DensityCaluclation#getError</code> to
         * display some manner of error to the user. If not in error call <code>DensityCaluclation#getResult</code> to retrieve the result.
         * @see DensityCaluclation
     */
    public DensityCaluclation getDensityFor(final int width, final int height, final double screenLength) {
        final ValidationResult widthValidation = validateInteger(WIDTH, width);
        final ValidationResult heightValidation = validateInteger(HEIGHT, height);
        final ValidationResult screenValidation = validateDouble(screenLength);

        if (widthValidation.isInvalid() || heightValidation.isInvalid() || screenValidation.isInvalid()) {
            return new DensityCaluclation(widthValidation.isInvalid() ? widthValidation.reason
                    : heightValidation.isInvalid() ? heightValidation.reason
                    : screenValidation.reason);
        } else {
            return new DensityCaluclation((int) Math.round(Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / screenLength));
        }
    }

    private ValidationResult validateDouble(final double screenLength) {
        final boolean invalid = screenLength <= 0 || screenLength >= MAX_SCREEN_DIAGONAL_SIZE;
        if (invalid) {
            return new ValidationResult(screenLength <= 0 ? SCREEN_DIAGONAL + " is less than 1" :
                    SCREEN_DIAGONAL + " is greater than max value: " + MAX_SCREEN_DIAGONAL_SIZE);
        } else {
            return new ValidationResult();
        }
    }

    private ValidationResult validateInteger(String attribute, final int size) {
        final boolean invalid = size <= 0 || size >= MAX_RESOLUTION_SIZE;
        if (invalid) {
            return new ValidationResult(size <= 0 ? (attribute + " is less than 1") :
                    (attribute + " is greater than max resolution size: " + MAX_RESOLUTION_SIZE));
        } else {
            return new ValidationResult();
        }
    }

    /**
         * Wrapper class for a calculation result. Always call <code>isValid</code> before using the result. If this object return false from <code>isValid</code> then the error that
         * occurred can be extracted through the <code>getError</code> method. In this state <code>getResult</code> will return a value of
         * <code>DensityCalculator#ERROR_VALUE</code>.
         * <p/>
         * If this object is valid then the result can be retrieved through <code>getResult</code>. Calling <code>getError</code> in this state will return the value
         *   of <code>DensityCalculator#NONE</code>.
     */
    public static final class DensityCaluclation {
        private final int result;
        private final String error;

        public DensityCaluclation(final int result) {
            this.result = result;
            error = NONE;
        }

        public DensityCaluclation(final String error) {
            this.error = error;
            result = ERROR_VALUE;
        }

        public boolean isValid() {
            return result != ERROR_VALUE;
        }

        public int getResult() {
            return result;
        }

        public String getError() {
            return error;
        }
    }

    private static final class ValidationResult {
        private final String reason;

        private ValidationResult() {
            reason = NONE;
        }

        private ValidationResult(final String reason) {
            this.reason = reason;
        }

        public boolean isInvalid() {
            return !reason.equals(NONE);
        }
    }
}

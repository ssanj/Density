/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.calc;

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

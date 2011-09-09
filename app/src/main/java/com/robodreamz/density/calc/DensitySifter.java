/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.calc;

public class DensitySifter {

    //ranges were calculated by multiplying through 0.125 for a given range. Eg. If the range was 40 points, then the min boundary would be (40x0.125) => next value -5 points.
    public enum DPI {
        LDPI(0, 155) /* ~ 120 */,
        MDPI(156, 229) /* ~ 160 */,
        HDPI(230, 310) /* ~ 240 */,
        XHDPI(311, 3200) /* ~ 320  */,
        NODPI(-1, -1) /* catch all value for when there is no match*/;

        private int min;
        private int max;

        DPI(final int min, final int upper) {
            this.min = min;
            this.max = upper;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public boolean contains(int dpi) {
            return getMin() <= dpi && getMax() >= dpi;
        }
    }

    public DPI sift(final int dpi) {
        for (DPI value : DPI.values()) {
            if (value != DPI.NODPI && value.contains(dpi)) {
                return value;
            }
        }

        return DPI.NODPI;
    }
}

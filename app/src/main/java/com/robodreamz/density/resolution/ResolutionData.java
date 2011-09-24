/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.DensityApplication;

public final class ResolutionData {

    private static final ResolutionItem[] RESOLUTIONS = {
        resolutionHeader(),
        resolutionElement(320, 240) /* Sony Xperia x10 mini pro*/,
        resolutionElement(400, 240) /* Samsung Gem*/,
        resolutionElement(480, 320),
        resolutionElement(540, 960),
        resolutionElement(800, 480),
        resolutionElement(854, 480),
        resolutionElement(960, 540),
        resolutionElement(1024, 600) /* Samsung Galaxy Tab*/,
        resolutionElement(1200, 800),
        customResolutionElement(410, 360),
        customResolutionElement(873, 510),
    };

    private static int invalidPosition = DensityApplication.getConstants().getInvalidPositionIndex();

    public static final IndexPair INDEX_PAIR = new IndexPair(invalidPosition, invalidPosition);

    private static ResolutionItem resolutionHeader() {
        return new ResolutionHeader();
    }

    private static StandardResolutionElement resolutionElement(int width, int height) {
        return new StandardResolutionElement(width, height);
    }

    private static CustomResolutionElement customResolutionElement(int width, int height) {
        return new CustomResolutionElement(resolutionElement(width, height));
    }

    public static ResolutionItem[] getData() {
        return RESOLUTIONS;
    }

    public static void reset() {
        INDEX_PAIR.update(invalidPosition);

        for (int index = 1; index < RESOLUTIONS.length; index++) {
            RESOLUTIONS[index].uncheck();
        }
    }
}

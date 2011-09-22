/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

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

    //TODO: We should get the -1 from Constants.
    public static final IndexPair INDEX_PAIR = new IndexPair(-1, -1);


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
        INDEX_PAIR.update(-1);

        for (int index = 1; index < RESOLUTIONS.length; index++) {
            RESOLUTIONS[index].uncheck();
        }
    }
}

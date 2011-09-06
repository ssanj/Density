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
    };

    private static ResolutionItem resolutionHeader() {
        return new ResolutionHeader();
    }

    private static ResolutionElement resolutionElement(int width, int height) {
        return new ResolutionElement(width, height);
    }

    public static ResolutionItem[] getData() {
        return RESOLUTIONS;
    }
}

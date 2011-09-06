/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import static com.robodreamz.density.resolution.Resolution.resolution;

public final class ResolutionData {

    private static final Resolution[] RESOLUTIONS = {
        resolution(320, 240) /* Sony Xperia x10 mini pro*/,
        resolution(400, 240) /* Samsung Gem*/,
        resolution(480, 320),
        resolution(540, 960),
        resolution(800, 480),
        resolution(854, 480),
        resolution(960, 540),
        resolution(1024, 600) /* Samsung Galaxy Tab*/,
        resolution(1200, 800),
    };

    public static Resolution[] getData() {
        return RESOLUTIONS;
    }
}

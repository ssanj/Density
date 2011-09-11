/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.screen.DefaultDensity;

public final class DensityResultFragmentSetup {

    private DefaultDensity defaultDensity;

    public DensityResultFragmentSetup(final DefaultDensity defaultDensity) {
        this.defaultDensity = defaultDensity;
    }

    public void setup() {
        defaultDensity.setValue();
    }
}

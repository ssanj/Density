/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.os.Bundle;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.fragment.DensityResultFragmentSetup;
import com.robodreamz.density.fragment.ResolutionListFragmentSetup;
import com.robodreamz.density.fragment.ScreenSizeFragmentSetup;
import com.robodreamz.density.screen.DefaultDensity;

public final class DensityAppActivity extends AbstractDensityActivty {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_screen);

        final DelegateFactory factory = DensityApplication.getFactory();
        final ActivityDelegate activityDelegate = factory.createActivityDelegate(this);
        final DefaultDensity defaultDensity = new DefaultDensity(activityDelegate);

        new ScreenSizeFragmentSetup(activityDelegate, factory).setup();
        new ResolutionListFragmentSetup(activityDelegate).setup();
        new DensityResultFragmentSetup(defaultDensity).setup();
    }
}

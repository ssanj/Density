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
import com.robodreamz.density.screen.DensityResultCalculator;

public final class DensityAppActivity extends AbstractDensityActivty {

    private ResolutionListFragmentSetup resolutionListFragmentSetup;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_screen);

        final DelegateFactory factory = DensityApplication.getFactory();
        final ActivityDelegate activityDelegate = createActivityDelegate(factory);
        final DefaultDensity defaultDensity = createDefaultDensity(activityDelegate);
        final DensityResultCalculator densityResultCalculator = createDensityResultCalcualtor(activityDelegate);


        new ScreenSizeFragmentSetup(activityDelegate, factory).setup();
        resolutionListFragmentSetup = new ResolutionListFragmentSetup(activityDelegate,
                densityResultCalculator, defaultDensity);
        resolutionListFragmentSetup.setup();
        new DensityResultFragmentSetup(defaultDensity).setup();
    }

    @Override protected void onResume() {
        super.onResume();
        if (resolutionListFragmentSetup == null) {
            final ActivityDelegate activity = createActivityDelegate(DensityApplication.getFactory());
            new ResolutionListFragmentSetup(activity, createDensityResultCalcualtor(activity), createDefaultDensity(activity)).onResume();
        }
    }

    private ActivityDelegate createActivityDelegate(final DelegateFactory factory) {
        return factory.createActivityDelegate(this);
    }

    private DefaultDensity createDefaultDensity(final ActivityDelegate activityDelegate) {
        return new DefaultDensity(activityDelegate);
    }

    private DensityResultCalculator createDensityResultCalcualtor(final ActivityDelegate activityDelegate) {
        return new DensityResultCalculator(
                activityDelegate,
                DensityApplication.getCalcualtor(),
                DensityApplication.getResolver(),
                DensityApplication.getSifter());
    }
}

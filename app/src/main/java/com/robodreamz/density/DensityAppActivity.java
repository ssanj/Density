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

        //We need to maintain the order of these "fragments" mainly because, multiple fragments can use the resolution list and update the
        //find density. So we must ensure that the resolution list and the density widgets are created before they are used.
        resolutionListFragmentSetup = new ResolutionListFragmentSetup(activityDelegate, densityResultCalculator);
        resolutionListFragmentSetup.setup();
        new DensityResultFragmentSetup(defaultDensity).setup();
        new ScreenSizeFragmentSetup(activityDelegate, factory).setup(densityResultCalculator);
    }

    @Override protected void onResume() {
        super.onResume();
        resolutionListFragmentSetup.onResume(DensityApplication.getConstants());
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
                DensityApplication.getSifter(),
                DensityApplication.getConstants());
    }
}

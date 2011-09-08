/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.os.Bundle;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.fragment.ResolutionListFragmentSetup;
import com.robodreamz.density.fragment.ScreenSizeFragmentSetup;

public final class DensityAppActivity extends AbstractDensityActivty {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_screen);
        final ActivityDelegate activityDelegate = DensityApplication.getFactory().createActivityDelegate(this);
        new ScreenSizeFragmentSetup(activityDelegate).setup();
        new ResolutionListFragmentSetup(activityDelegate).setup();
    }
}

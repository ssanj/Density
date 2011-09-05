/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityAppActivity;

public final class DensityAppRobTest extends ActivityInstrumentationTestCase2<DensityAppActivity> {

    private Solo solo;

    public DensityAppRobTest() {
        super("com.robodreamz.density", DensityAppActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testShouldHaveDefaultValueSetForScreenSize() {
        assertTrue("DensityAppActivity did not show up", solo.waitForActivity("DensityAppActivity", 2000));
        assertTrue("Did not find default screen size", solo.searchText("2.5"));
        assertTrue("Did not find screen size unit", solo.searchText("\""));
    }
}

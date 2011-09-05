/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ProgressBar;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityAppActivity;

import java.util.ArrayList;

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

    public void testShouldUpdateScreenSizeOnMovingSliders() {
        assertTrue("DensityAppActivity did not show up", solo.waitForActivity("DensityAppActivity", 2000));
        final ArrayList<ProgressBar> currentProgressBars = solo.getCurrentProgressBars();
        assertEquals("Incorrect number of progress bars on screen", 2, currentProgressBars.size());
        solo.setProgressBar(currentProgressBars.get(0), 5);
        solo.setProgressBar(currentProgressBars.get(1), 4);
        assertTrue("Did not find expected screen size of 7.4", solo.searchText("7.4"));
    }
}

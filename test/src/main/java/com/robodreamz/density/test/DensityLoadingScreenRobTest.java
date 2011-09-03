/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityAppActivity;
import com.robodreamz.density.DensityLoadingScreenActivity;

public final class DensityLoadingScreenRobTest extends ActivityInstrumentationTestCase2<DensityLoadingScreenActivity> {

    private Solo solo;

    public DensityLoadingScreenRobTest() {
        super("com.robodreamz.density", DensityLoadingScreenActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        assertTrue("DensityLoadingScreenActivity did not show up.", solo.waitForActivity("DensityLoadingScreenActivity", 500));
    }

    public void testAppIsStartedAfterLoadingScreen() {
        assertTrue("DensityAppActivity did not show up", solo.waitForActivity("DensityAppActivity", 2000));
        //when we go back the  DensityLoadingScreenActivity should have finished.
        solo.goBack();
        assertTrue(solo.getActivityMonitor().getLastActivity().getClass().getSimpleName().equals("DensityAppActivity"));
    }

    @Override protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        //we don't call getActivity.finish() here because the above test already kills the activity.
        super.tearDown();
    }
}

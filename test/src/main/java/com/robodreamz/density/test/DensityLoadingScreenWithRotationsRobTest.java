/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityLoadingScreenActivity;

public final class DensityLoadingScreenWithRotationsRobTest extends ActivityInstrumentationTestCase2<DensityLoadingScreenActivity> {

    private Solo solo;
    private Instrumentation.ActivityMonitor appCountmonitor;

    public DensityLoadingScreenWithRotationsRobTest() {
        super("com.robodreamz.density", DensityLoadingScreenActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        appCountmonitor = getInstrumentation().addMonitor(
                "com.robodreamz.density.DensityAppActivity", null, false);
        assertEquals("Has an existing DensityAppActivity", 0, appCountmonitor.getHits());

        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testRotationsDoNotLeadToTheAppStartingMultipleTimes() {
        assertTrue("DensityLoadingScreenActivity did not show up.", solo.waitForActivity("DensityLoadingScreenActivity", 500));

        final int currentOrientation = solo.getCurrentActivity().getRequestedOrientation();
        switch (currentOrientation) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                solo.sleep(100);
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                solo.sleep(100);
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                solo.sleep(100);
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                solo.sleep(100);
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                solo.sleep(100);
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                solo.sleep(100);
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        assertTrue("DensityAppActivity did not show up", solo.waitForActivity("DensityAppActivity", 2000));
        assertEquals("Got multiple DensityAppActivities", 1, appCountmonitor.getHits());
    }

    @Override protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            getInstrumentation().removeMonitor(appCountmonitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //we don't call getActivity.finish() here because the above test already kills the activity.
        super.tearDown();
    }
}

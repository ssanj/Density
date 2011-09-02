/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.test.ActivityInstrumentationTestCase2;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityLoadingScreenActivity;

public final class DensityLoadingScreenRobTest extends ActivityInstrumentationTestCase2<DensityLoadingScreenActivity> {

    private Solo solo;

    public DensityLoadingScreenRobTest() {
        super("com.robodreamz.density", DensityLoadingScreenActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testLoadingScreen() {
        assertTrue("DensityLoadingScreenActivity did not show up after 2s.", solo.waitForActivity("DensityLoadingScreenActivity", 500));
    }

    public void testAppIsStartedAfterLoadingScreen() {
        solo.waitForActivity("DensityLoadingScreenActivity", 500);
        assertTrue("DensityAppActivity did not show up after 2s.", solo.waitForActivity("DensityAppActivity", 2000));
        solo.goBack();
        solo.waitForActivity("DensityLoadingScreenActivity", 500);
        assertFalse("The application should have exited after back was pressed.",
                solo.getCurrentActivity().getClass().getSimpleName().equals("DensityLoadingScreenActivity"));
    }

    @Override protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        getActivity().finish();
        super.tearDown();
    }
}

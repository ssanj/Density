/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityAppActivity;
import com.robodreamz.density.R;
import com.robodreamz.density.calc.DensitySifter;

import java.util.ArrayList;
import java.util.List;

//TODO: This class can't extend AbstractDensityAppTest. It current segfaults.Seems to work this way though.
public final class DensityAppWithScreenSizeRobTest extends ActivityInstrumentationTestCase2<DensityAppActivity> {

    private Solo solo;

    public DensityAppWithScreenSizeRobTest() {
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
        final ArrayList<ProgressBar> currentProgressBars = waitForActivityAndAssertProgressBars();

        solo.setProgressBar(currentProgressBars.get(0), 5);
        solo.setProgressBar(currentProgressBars.get(1), 4);
        assertTrue("Did not find expected screen size of 7.4", solo.searchText("7.4"));
    }

    private ArrayList<ProgressBar> waitForActivityAndAssertProgressBars() {
        assertTrue("DensityAppActivity did not show up", solo.waitForActivity("DensityAppActivity", 2000));
        final ArrayList<ProgressBar> currentProgressBars = solo.getCurrentProgressBars();
        assertEquals("Incorrect number of progress bars on screen", 2, currentProgressBars.size());
        return currentProgressBars;
    }

    public void testShouldUpdateScreenSizeOnBoundaries() {
        final ArrayList<ProgressBar> currentProgressBars = waitForActivityAndAssertProgressBars();

        solo.setProgressBar(currentProgressBars.get(0), 10);
        solo.setProgressBar(currentProgressBars.get(1), 9);
        assertTrue("Did not find expected screen size of 7.4", solo.searchText("12.9"));

        solo.setProgressBar(currentProgressBars.get(0), 0);
        solo.setProgressBar(currentProgressBars.get(1), 0);
        assertTrue("Did not find expected screen size of 7.4", solo.searchText("2.0"));
    }

    public void testShouldMaintainProgressValuesOnRotation() {
        final ArrayList<ProgressBar> currentProgressBars = waitForActivityAndAssertProgressBars();

        solo.setProgressBar(currentProgressBars.get(0), 1);
        solo.setProgressBar(currentProgressBars.get(1), 2);
        assertTrue("Did not find expected screen size of 7.4", solo.searchText("3.2"));

        final Activity currentActivity = solo.getCurrentActivity();
        switch (currentActivity.getRequestedOrientation()) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                solo.setActivityOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        assertTrue("Did not find expected screen size of 7.4", solo.searchText("3.2"));
    }

    public void testShouldUpdateDensityIfAResolutionItemIsSelected() {
        final List<ProgressBar> currentProgressBars = waitForActivityAndAssertProgressBars();

        final List<TextView> textViews = solo.clickInList(4);
        assertEquals("Incorrect width", "480", textViews.get(0).getText());
        assertEquals("Incorrect height", "320", textViews.get(2).getText());

        solo.setProgressBar(currentProgressBars.get(0), 1);//3
        solo.setProgressBar(currentProgressBars.get(1), 5);//.5

        final TextView densityValue = (TextView) solo.getCurrentActivity().findViewById(R.id.density_result_density_value_text);
        final TextView densityCategory = (TextView) solo.getCurrentActivity().findViewById(R.id.density_result_density_value_category);

        assertEquals("Incorrect density value", "165", densityValue.getText());
        assertEquals("Incorrect density category", DensitySifter.DPI.MDPI.toString(), densityCategory.getText());
    }

    @Override protected void tearDown() throws Exception {
        try {
            solo.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.tearDown();
    }
}

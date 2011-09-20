/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Suppress;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityAppActivity;
import com.robodreamz.density.R;
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.resolution.ResolutionElement;

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

    public void testShouldUpdateDensityIfAResolutionItemIsSelected() throws Throwable {
        final List<ProgressBar> currentProgressBars = waitForActivityAndAssertProgressBars();
        final int index = 3;
        final ListView resolutionList = getResolutionList();
        runTestOnUiThread(new Runnable() {
            @Override public void run() {
                resolutionList.performItemClick(null, index, index);
            }
        });

        final ResolutionElement item = (ResolutionElement) resolutionList.getAdapter().getItem(index);
        assertEquals("Incorrect width", "480", String.valueOf(item.width));
        assertEquals("Incorrect height", "320", String.valueOf(item.height));

        solo.setProgressBar(currentProgressBars.get(0), 1);//3
        solo.setProgressBar(currentProgressBars.get(1), 5);//.5

        final TextView densityValue = (TextView) solo.getCurrentActivity().findViewById(R.id.density_result_density_value_text);
        final TextView densityCategory = (TextView) solo.getCurrentActivity().findViewById(R.id.density_result_density_value_category);

        assertEquals("Incorrect density value", "165", densityValue.getText());
        assertEquals("Incorrect density category", DensitySifter.DPI.MDPI.toString(), densityCategory.getText());
    }

    protected ListView getResolutionList() {
        final ListView listView = (ListView) solo.getCurrentActivity().findViewById(R.id.app_screen_resolution_list);
        assertNotNull("ResolutionList is null", listView);
        return listView;
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

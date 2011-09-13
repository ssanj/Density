/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.content.pm.ActivityInfo;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.robodreamz.density.R;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.test.common.DensityResult;
import com.robodreamz.density.test.common.Resolution;
import com.robodreamz.density.test.common.ScreenSize;

import java.util.List;

public final class DensityAppWithDensityResultOnRotationRobTest extends AbstractDensityAppTest {

    public void testShouldCalculateDensityWhenResolutionIsChosen() throws Throwable {
        waitForApplicationActivity();
        assertResolutionChangesAreMaintainedOnOrientation(1, new ScreenSize(3, 2), new DensityResult(125, "LDPI"), new Resolution(320, 240));
        assertResolutionChangesAreMaintainedOnOrientation(3, new ScreenSize(3, 1), new DensityResult(186, "MDPI"), new Resolution(480, 320));
        assertResolutionChangesAreMaintainedOnOrientation(5, new ScreenSize(3, 1), new DensityResult(301, "HDPI"), new Resolution(800, 480));
        assertResolutionChangesAreMaintainedOnOrientation(8, new ScreenSize(3, 5), new DensityResult(339, "XHDPI"), new Resolution(1024, 600));
    }

    private void assertResolutionChangesAreMaintainedOnOrientation(final int index, final ScreenSize screenSize, final DensityResult densityResult,
                                                                   final Resolution resolution) throws Throwable {
        final List<ListView> currentListViews = solo.getCurrentListViews();
        assertEquals("The number of ListsViews on the screen is incorrect", 1, currentListViews.size());
        assertTrue("The number of resolutions is incorrect", currentListViews.get(0).getCount() > 1);

        setAndAssertSliders(screenSize);
        clickOnListAndAssert(index, resolution);
        assertDensityCalc(densityResult);

        assertDensityResultOnOrientation(index, screenSize, densityResult, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertDensityResultOnOrientation(index, screenSize, densityResult, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void assertDensityResultOnOrientation(final int index, final ScreenSize screenSize, final DensityResult densityResult, final int screenOrientationLandscape) {
        solo.setActivityOrientation(screenOrientationLandscape);
        assertSliders(screenSize);
        assertResolutionCheckState((ListView) solo.getCurrentActivity().findViewById(R.id.app_screen_resolution_list), index);
        assertDensityCalc(densityResult);
    }

    private void assertDensityCalc(final DensityResult densityResult) {
        final TextView resultValue = (TextView) solo.getCurrentActivity().findViewById(R.id.density_result_density_value_text);
        final TextView resultCategory = (TextView) solo.getCurrentActivity().findViewById(R.id.density_result_density_value_category);
        assertEquals("Incorrect density value: " + densityResult.getValueAsString(), densityResult.getValueAsString(), resultValue.getText());
        assertEquals("Incorrect density value category: " + densityResult.category, densityResult.category, resultCategory.getText());
    }

    private void assertResolutionCheckState(final ListView listView, final int index) {
        waitFor(50);//wait here for selections to be updated
        //we reduce the index by 1 here because robotium list click indexes are 1-based and our list selections are 0-based! What tha?
        assertEquals("Incorrect resolution selected", index, ResolutionData.INDEX_PAIR.getCurrentIndex());
        final ResolutionElement element = (ResolutionElement) listView.getAdapter().getItem(ResolutionData.INDEX_PAIR.getCurrentIndex());
        assertTrue("Incorrect resolution item: " + index + ", is not checked", element.isChecked());
    }

    private void waitFor(final int timeout) {
        solo.waitForText("", 1, timeout);
    }

    private void clickOnListAndAssert(final int index, final Resolution resolution) throws Throwable {
        final ListView listView = (ListView) solo.getCurrentActivity().findViewById(R.id.app_screen_resolution_list);
        assertNotNull("ResolutionList is null", listView);

        this.runTestOnUiThread(new Runnable() {
            @Override public void run() {
                listView.performItemClick(null, index, index);
            }
        });

        assertResolutionCheckState(listView, index);
    }

    private String getText(final List<TextView> textViews) {
        final StringBuilder sb = new StringBuilder();
        for (TextView tv :  textViews) {
            sb.append(tv.getId()).append("-").append(tv.getText()).append(", ");
        }

        return sb.toString();
    }

    private void setAndAssertSliders(final ScreenSize screenSize) {
        final List<ProgressBar> currentProgressBars = solo.getCurrentProgressBars();
        solo.setProgressBar(currentProgressBars.get(0), screenSize.integral);
        solo.setProgressBar(currentProgressBars.get(1), screenSize.decimal);
        assertSliders(screenSize);
    }

    private void assertSliders(final ScreenSize screenSize) {
        assertTrue("Did not find expected screen size of " + screenSize.getScreenSize(), solo.searchText(screenSize.getScreenSize()));
    }
}

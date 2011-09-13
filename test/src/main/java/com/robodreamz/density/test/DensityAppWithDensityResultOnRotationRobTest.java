/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.content.pm.ActivityInfo;
import android.test.suitebuilder.annotation.Suppress;
import android.view.KeyEvent;
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

    @Suppress public void testShouldCalculateDensityWhenResolutionIsChosenByClick() throws Throwable {
        waitForApplicationActivity();
        assertResolutionChangesAreMaintainedOnOrientationWhenClicked(
                1, new ScreenSize(3, 2), new Resolution(320, 240), new DensityResult(125, "LDPI"));
        assertResolutionChangesAreMaintainedOnOrientationWhenClicked(
                3, new ScreenSize(3, 1), new Resolution(480, 320), new DensityResult(186, "MDPI"));
        assertResolutionChangesAreMaintainedOnOrientationWhenClicked(
                5, new ScreenSize(3, 1), new Resolution(800, 480), new DensityResult(301, "HDPI"));
        assertResolutionChangesAreMaintainedOnOrientationWhenClicked(
                8, new ScreenSize(3, 5), new Resolution(1024, 600), new DensityResult(339, "XHDPI"));
    }

    public void testShouldCalculateDensityWhenResolutionIsChosenBySelection() throws Throwable {
        assertResolutionChangesAreMaintainedOnOrientationWhenSelected(
                1, new ScreenSize(3, 2), new Resolution(320, 240), new DensityResult(125, "LDPI"));
        assertResolutionChangesAreMaintainedOnOrientationWhenSelected(
                3, new ScreenSize(3, 1), new Resolution(480, 320), new DensityResult(186, "MDPI"));
        assertResolutionChangesAreMaintainedOnOrientationWhenSelected(
                5, new ScreenSize(3, 1), new Resolution(800, 480), new DensityResult(301, "HDPI"));
        assertResolutionChangesAreMaintainedOnOrientationWhenSelected(
                8, new ScreenSize(3, 5), new Resolution(1024, 600), new DensityResult(339, "XHDPI"));
    }

    private void assertResolutionChangesAreMaintainedOnOrientationWhenSelected(final int index, final ScreenSize screenSize,
                                                                   final Resolution resolution, final DensityResult densityResult) throws Throwable {
        waitForApplicationActivity();

        setAndAssertSliders(screenSize);
        selectListItemAndAssert(index, resolution);
        assertDensityCalc(densityResult);
        assertDensityResultOnOrientationForSelects(index, screenSize, densityResult, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertDensityResultOnOrientationForSelects(index, screenSize, densityResult, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void selectListItemAndAssert(final int index, final Resolution resolution) throws Throwable {
        final ListView resolutionList = getResolutionList();
        runTestOnUiThread(new Runnable() {
            @Override public void run() {
                resolutionList.requestFocus();
                resolutionList.setSelection(0); //select first item.
            }
        });

        //send index -1 key strokes because we start @ the first list item above.
        sendRepeatedKeys(index - 1, KeyEvent.KEYCODE_DPAD_DOWN);
        assertResolutionSelectState(resolutionList, index);
    }

    private void assertResolutionChangesAreMaintainedOnOrientationWhenClicked(final int index, final ScreenSize screenSize, final Resolution resolution, final DensityResult densityResult) throws Throwable {
        setAndAssertSliders(screenSize);
        clickOnListAndAssert(index, resolution);
        assertDensityCalc(densityResult);

        assertDensityResultOnOrientationForClicks(index, screenSize, densityResult, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        assertDensityResultOnOrientationForClicks(index, screenSize, densityResult, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void assertDensityResultOnOrientationForClicks(final int index, final ScreenSize screenSize, final DensityResult densityResult,
                                                           final int screenOrientationLandscape) {
        solo.setActivityOrientation(screenOrientationLandscape);
        assertSliders(screenSize);
        assertResolutionCheckState((ListView) solo.getCurrentActivity().findViewById(R.id.app_screen_resolution_list), index);
        assertDensityCalc(densityResult);
    }

    private void assertDensityResultOnOrientationForSelects(final int index, final ScreenSize screenSize, final DensityResult densityResult,
                                                           final int screenOrientationLandscape) {
        solo.setActivityOrientation(screenOrientationLandscape);
        assertSliders(screenSize);
        assertResolutionSelectState((ListView) solo.getCurrentActivity().findViewById(R.id.app_screen_resolution_list), index);
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

        assertEquals("Incorrect resolution selected", index, ResolutionData.INDEX_PAIR.getCurrentIndex());
        final ResolutionElement element = (ResolutionElement) listView.getAdapter().getItem(ResolutionData.INDEX_PAIR.getCurrentIndex());
        assertTrue("Incorrect resolution item: " + index + ", is not checked", element.isChecked());
    }

    private void assertResolutionSelectState(final ListView listView, final int index) {
        waitFor(50);//wait here for selections to be updated
        //we reduce the index by 1 here because we give focus to the first list item by default. So the number of items to move is reduced by 1.
        assertEquals("Incorrect resolution selected", index, listView.getSelectedItemPosition());
        final ResolutionElement element = (ResolutionElement) listView.getAdapter().getItem(index);
        assertTrue("Incorrect resolution item: " + index + ", is not checked", element.isChecked());
    }

    private void waitFor(final int timeout) {
        solo.waitForText("", 1, timeout);
    }

    private void clickOnListAndAssert(final int index, final Resolution resolution) throws Throwable {
        final ListView listView = getResolutionList();

        this.runTestOnUiThread(new Runnable() {
            @Override public void run() {
                listView.performItemClick(null, index, index);
            }
        });

        assertResolutionCheckState(listView, index);
    }

    private ListView getResolutionList() {
        final ListView listView = (ListView) solo.getCurrentActivity().findViewById(R.id.app_screen_resolution_list);
        assertNotNull("ResolutionList is null", listView);
        return listView;
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

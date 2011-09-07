/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.test.suitebuilder.annotation.Suppress;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.robodreamz.density.R;

import java.util.List;

public final class DensityAppWithDensityResultRobTest extends AbstractDensityAppTest {

    public void testShouldContainDensityResult() {
        waitForApplicationActivity();
        TextView resultValueText = (TextView) getActivity().findViewById(R.id.density_result_density_value_text);
        TextView resultValueUnit = (TextView) getActivity().findViewById(R.id.density_result_density_value_unit);
        TextView resultValueCategory = (TextView) getActivity().findViewById(R.id.density_result_density_value_category);
        assertNotNull("Could not find density result value", resultValueText);
        assertNotNull("Could not find density result unit", resultValueUnit);
        assertNotNull("Could not find density result category", resultValueCategory);
    }

    @Suppress //TODO:Fix this
    public void testShouldCalculateDensityWhenResolutionIsChosen() {
        waitForApplicationActivity();
        final List<ProgressBar> currentProgressBars = solo.getCurrentProgressBars();

        solo.setProgressBar(currentProgressBars.get(0), 1);
        solo.setProgressBar(currentProgressBars.get(1), 2);
        assertTrue("Did not find expected screen size of 3.2", solo.searchText("3.2"));

        final List<ListView> currentListViews = solo.getCurrentListViews();
        assertEquals("The number of ListsViews on the screen is incorrect", 1, currentListViews.size());
        assertTrue("The number of resolutions is incorrect", currentListViews.get(0).getCount() > 1);
        solo.clickInList(1, 0); //select the first item. Assume for now that it's 320x240

        TextView resultValueText = (TextView) getActivity().findViewById(R.id.density_result_density_value_text);
        TextView resultValueUnit = (TextView) getActivity().findViewById(R.id.density_result_density_value_unit);
        TextView resultValueCategory = (TextView) getActivity().findViewById(R.id.density_result_density_value_category);

        assertEquals("Incorrect density value", "125", resultValueText.getText());
        assertEquals("Incorrect density value unit", "dpi", resultValueUnit.getText());
        assertEquals("Incorrect density value category", "ldpi", resultValueCategory.getText());
    }
}
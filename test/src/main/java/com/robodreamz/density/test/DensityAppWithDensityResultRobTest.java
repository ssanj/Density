/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.robodreamz.density.DensityApplication;
import com.robodreamz.density.R;
import com.robodreamz.density.screen.ScreenSizeResolver;

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

    public void testShouldCalculateDensityWhenResolutionIsChosen() {
        waitForApplicationActivity();
        final List<ListView> currentListViews = solo.getCurrentListViews();
        assertEquals("The number of ListsViews on the screen is incorrect", 1, currentListViews.size());
        assertTrue("The number of resolutions is incorrect", currentListViews.get(0).getCount() > 1);
        assertDensityCalc(2, new ScreenSize(3, 2), new Resolution(320, 240), new DensityResult(125, "LDPI"));
        assertDensityCalc(4, new ScreenSize(3, 1), new Resolution(480, 320), new DensityResult(186, "MDPI"));
        assertDensityCalc(6, new ScreenSize(3, 1), new Resolution(800, 480), new DensityResult(301, "HDPI"));
        assertDensityCalc(9, new ScreenSize(3, 5), new Resolution(1024, 600), new DensityResult(339, "XHDPI"));
    }

    private void assertDensityCalc(final int index, final ScreenSize screenSize, final Resolution reso, final DensityResult densityResult) {
        final List<ProgressBar> currentProgressBars = solo.getCurrentProgressBars();
        solo.setProgressBar(currentProgressBars.get(0), screenSize.integral);
        solo.setProgressBar(currentProgressBars.get(1), screenSize.decimal);
        assertTrue("Did not find expected screen size of " + screenSize.getScreenSize(), solo.searchText(screenSize.getScreenSize()));

        final List<TextView> textViews = solo.clickInList(index, 0);

        assertEquals("Incorrect number of TextViews returned", 3, textViews.size());
        assertEquals("Incorrect Width returned: " + reso.getWidthAsString(), reso.getWidthAsString(), textViews.get(0).getText());
        assertEquals("Incorrect separator returned", getActivity().getResources().getString(R.string.resolution_separator), textViews.get(1).getText());
        assertEquals("Incorrect Height returned: " + reso.getHeightAsString(), reso.getHeightAsString(), textViews.get(2).getText());
        assertTrue("Incorrect density value: " + densityResult.getValueAsString(), solo.waitForText(densityResult.getValueAsString(), 1, 2000));
        assertTrue("Incorrect density value category: " + densityResult.category, solo.waitForText(densityResult.category, 1, 2000));
    }

    private final static class ScreenSize {
        private final int integral;
        private final int decimal;

        private ScreenSize(final int integral, final int decimal) {
            this.integral = integral - ScreenSizeResolver.INTEGRAL_SCREENSIZE_OFFSET;
            this.decimal = decimal;
        }

        String getScreenSize() {
            return DensityApplication.getResolver().convertProgressValueToActualString(integral, decimal).toString();
        }
    }

    private final static class Resolution {
        private final int width;
        private final int height;

        private Resolution(final int width, final int height) {
            this.width = width;
            this.height = height;
        }

        String getWidthAsString() {
            return String.valueOf(width);
        }

        String getHeightAsString() {
            return String.valueOf(height);
        }
    }

    private static final class DensityResult {
        private final int value;
        private final String category;

        private DensityResult(final int value, final String category) {
            this.value = value;
            this.category = category;
        }

        String getValueAsString() {
            return String.valueOf(value);
        }
    }
}
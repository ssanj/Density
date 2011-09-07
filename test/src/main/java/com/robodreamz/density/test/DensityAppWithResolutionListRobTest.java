/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.ListView;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityAppActivity;
import com.robodreamz.density.R;

import java.util.List;

public final class DensityAppWithResolutionListRobTest extends ActivityInstrumentationTestCase2<DensityAppActivity> {

    private Solo solo;

    public DensityAppWithResolutionListRobTest() {
        super("com.robodreamz.density", DensityAppActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testResolutionsAreDisplayed() {
        assertTrue("DensityAppActivity did not show up", solo.waitForActivity("DensityAppActivity", 2000));
        final List<ListView> currentListViews = solo.getCurrentListViews();
        assertEquals("The number of ListsViews on the screen is incorrect", 1, currentListViews.size());
        assertTrue("The number of resolutions is incorrect", currentListViews.get(0).getCount() > 1);
        final List<TextView> headers = solo.clickInList(0, 0);
        assertEquals("Incorrect number of header data", 1, headers.size());
        assertEquals("Incorrect header text", getActivity().getResources().getString(R.string.resolution_list_header_element_title), headers.get(0).getText());
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

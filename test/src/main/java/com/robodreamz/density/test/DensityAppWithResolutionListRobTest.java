/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.widget.ListView;
import android.widget.TextView;
import com.robodreamz.density.R;

import java.util.List;

public final class DensityAppWithResolutionListRobTest extends AbstractDensityAppTest {

    public void testResolutionsAreDisplayed() {
        waitForApplicationActivity();
        assertTrue("Did not find header text", solo.waitForText("Common", 1, 2000, true));

        final List<ListView> currentListViews = solo.getCurrentListViews();
        assertEquals("The number of ListsViews on the screen is incorrect", 1, currentListViews.size());
        assertTrue("The number of resolutions is incorrect", currentListViews.get(0).getCount() > 1);
        final List<TextView> headers = solo.clickInList(0, 0);
        assertEquals("Incorrect number of header data", 2, headers.size());
        assertEquals("Incorrect header text", getActivity().getResources().getString(R.string.resolution_list_header_element_title), headers.get(0).getText());
    }
}

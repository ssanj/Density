/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;
import com.jayway.android.robotium.solo.Solo;
import com.robodreamz.density.DensityAppActivity;
import com.robodreamz.density.R;
import com.robodreamz.density.resolution.ResolutionData;

public abstract class AbstractDensityAppTest extends ActivityInstrumentationTestCase2<DensityAppActivity> {

    protected Solo solo;

    public AbstractDensityAppTest() {
        super("com.robodreamz.density", DensityAppActivity.class);
    }

    @Override protected void setUp() throws Exception {
        ResolutionData.reset();
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    protected void waitForApplicationActivity() {
        assertTrue("DensityAppActivity did not show up", solo.waitForActivity("DensityAppActivity", 2000));
    }

    protected ListView getResolutionList() {
        final ListView listView = (ListView) solo.getCurrentActivity().findViewById(R.id.app_screen_resolution_list);
        assertNotNull("ResolutionList is null", listView);
        return listView;
    }

   @Override protected void tearDown() throws Exception {
        try {
            solo.finalize();
            ResolutionData.reset();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.tearDown();
    }
}

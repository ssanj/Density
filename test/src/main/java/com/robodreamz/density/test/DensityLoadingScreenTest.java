/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.robodreamz.density.DensityLoadingScreenActivity;
import com.robodreamz.density.R;

public final class DensityLoadingScreenTest extends ActivityInstrumentationTestCase2<DensityLoadingScreenActivity> {

    private DensityLoadingScreenActivity activity;
    private TextView loadingText;

    public DensityLoadingScreenTest() {
        super("com.robodreamz.density", DensityLoadingScreenActivity.class);
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
        loadingText = (TextView) activity.findViewById(R.id.loading_screen_text);
    }

    public void testDisplayLoadingText() {
        assertEquals("Loading text is not as expected.", loadingText.getText(), "Medium Density");
    }

    public void testLoadingTextOnScreen() {
        final Window window = activity.getWindow();
        final View decorView = window.getDecorView();
        ViewAsserts.assertOnScreen(decorView, loadingText);
    }

    public void testPreconditions() {
        assertNotNull(activity);
        assertNotNull(loadingText);
    }

    @Override protected void tearDown() throws Exception {
        super.tearDown();
    }
}


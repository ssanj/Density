/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.test;

import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.test.MoreAsserts;
import android.test.ViewAsserts;
import android.util.Log;
import android.view.Gravity;
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
        assertEquals("Loading text is not as expected.", loadingText.getText(), activity.getResources().getString(R.string.app_name));
    }

    public void testLoadingTextOnScreen() {
        final Window window = activity.getWindow();
        final View decorView = window.getDecorView();
        ViewAsserts.assertOnScreen(decorView, loadingText);
    }
    public void testLoadingTextIsCentredOnScreen() {
        assertEquals("Gravity", loadingText.getGravity(), Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
    }

    public void testPreconditions() {
        assertNotNull(activity);
        assertNotNull(loadingText);
    }

    @Override protected void tearDown() throws Exception {
        super.tearDown();
    }
}


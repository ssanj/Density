/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.widget.ProgressBar;
import android.widget.TextView;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public final class DensityAppScreenTest {

    private DensityAppActivity activity;
    private TextView screenSizeLabelText;
    private ProgressBar screenSizeIntegerProgress;
    private ProgressBar screenSizeDecimalProgress;
    private TextView screenSizeValueText;
    private TextView screenSizeUnitText;

    @Before public void setup() throws Exception {
        activity = new DensityAppActivity();
        activity.onCreate(null);
        screenSizeLabelText = (TextView) activity.findViewById(R.id.app_screen_screensize_label_text);
        screenSizeIntegerProgress = (ProgressBar) activity.findViewById(R.id.app_screen_screensize_integer_progress);
        screenSizeDecimalProgress = (ProgressBar) activity.findViewById(R.id.app_screen_screensize_decimal_progress);
        screenSizeValueText = (TextView) activity.findViewById(R.id.app_screen_screensize_value_text);
        screenSizeUnitText = (TextView) activity.findViewById(R.id.app_screen_screensize_unit_text);
    }

    @Test public void shouldContainScreenSizeWidgets() {
        assertNotNull(screenSizeLabelText);
        assertNotNull(screenSizeIntegerProgress);
        assertNotNull(screenSizeDecimalProgress);
        assertNotNull(screenSizeValueText);
        assertNotNull(screenSizeUnitText);
    }
}

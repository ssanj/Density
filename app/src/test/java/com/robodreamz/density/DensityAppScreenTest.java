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

import static org.junit.Assert.assertEquals;
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
        assertNotNull("app_screen_screensize_label_text is null", screenSizeLabelText);
        assertNotNull("app_screen_screensize_integer_progress is null", screenSizeIntegerProgress);
        assertNotNull("app_screen_screensize_decimal_progress is null", screenSizeDecimalProgress);
        assertNotNull("app_screen_screensize_value_text is null", screenSizeValueText);
        assertNotNull("app_screen_screensize_unit_text is null", screenSizeUnitText);
    }

    @Test public void screenSizeWidgetsShouldHaveExpectedDefaults() {
        assertDefaultStringValue(R.string.screen_size_label_text, screenSizeLabelText.getText(), "app_screen_screensize_label_text");
        assertDefaultStringValue(R.string.app_screen_screensize_unit_text, screenSizeUnitText.getText(), "app_screen_screensize_unit_text");
    }

    private void assertDefaultStringValue(final int resourceId, final CharSequence text, final String resourceName) {
        assertEquals(resourceName + " does not match default", activity.getResources().getString(resourceId), text);
    }

    private void assertDefaultIntegerValue(final int resourceId, final int value, final String resourceName) {
        assertEquals(resourceName + " does not match default", activity.getResources().getInteger(resourceId), value);
    }
}

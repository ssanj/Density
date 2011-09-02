/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.widget.TextView;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public final class DensityLoadingScreenActivityTest {

    private DensityLoadingScreenActivity activity;
    private TextView text;

    @Before public void setup() throws Exception {
        activity = new DensityLoadingScreenActivity();
        activity.onCreate(null);
        text = (TextView) activity.findViewById(R.id.loading_screen_text);
    }

    @Test public void shouldDisplayTextOnTheLoadingScreen() {
        assertThat("Loading screen text is null.", text, notNullValue());
        assertThat(text.getText().toString(), equalTo("Density"));
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.Activity;
import android.os.Bundle;

public final class DensityAppActivity extends Activity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_screen);
    }
}

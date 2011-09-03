/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.Activity;
import android.os.Bundle;

public abstract class AbstractDensityActivty extends Activity {

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDensityApp().incWindowCount();
    }

    protected DensityApplication getDensityApp() {
        return (DensityApplication) getApplication();
    }

//    //if the back button is pressed from either activity  then reinitialize the app so it can be relaunched.
    @Override public void onBackPressed() {
        super.onBackPressed();
        getDensityApp().setAppLaunched(false);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        getDensityApp().decWindowCount();
    }
}

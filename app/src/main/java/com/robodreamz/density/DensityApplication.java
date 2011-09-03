/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.Application;

public final class DensityApplication extends Application {

    private boolean appScheduled;

    protected synchronized void setAppScheduled(boolean scheduled) {
        appScheduled = scheduled;
    }

    protected synchronized boolean isAppScheduled() {
        return appScheduled;
    }

}

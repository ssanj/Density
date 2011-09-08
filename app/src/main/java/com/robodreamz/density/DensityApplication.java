/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.Application;
import android.util.Log;
import com.robodreamz.density.calc.DensityCalculator;
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.screen.ScreenSizeResolver;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class DensityApplication extends Application {

    private static final DelegateFactory FACTORY = new DelegateFactory();
    private static final DensityCalculator CALCUALTOR = new DensityCalculator();
    private static final DensitySifter SIFTER = new DensitySifter();
    private static final ScreenSizeResolver RESOLVER = new ScreenSizeResolver();
    public static final String TAG = "DensityApplication";

    private final AtomicInteger windowCount = new AtomicInteger(0);
    private final AtomicBoolean appLaunched = new AtomicBoolean(false);
    private final Long loadingScreenPause = 1000l;

    public void incWindowCount() {
        windowCount.getAndIncrement();
        Log.i(TAG, "Inc Window count to: " + windowCount.get());
    }

    public void decWindowCount() {
        windowCount.getAndDecrement();
        Log.i(TAG, "Dec Window count to: " + windowCount.get());

        if (windowCount.intValue() == 0) {
            setAppLaunched(false);
        }
    }

    public long getLoadingScreenPause() {
        return loadingScreenPause;
    }

    public void setAppLaunched(boolean scheduled) {
        appLaunched.getAndSet(scheduled);
    }

    public boolean isAppLaunched() {
        return appLaunched.get();
    }

    public static DelegateFactory getFactory() {
        return FACTORY;
    }

    public static DensityCalculator getCalcualtor() {
        return CALCUALTOR;
    }

    public static DensitySifter getSifter() {
        return SIFTER;
    }

    public static ScreenSizeResolver getResolver() {
        return RESOLVER;
    }
}

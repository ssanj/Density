/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.Application;
import android.util.Log;
import com.robodreamz.density.calc.DensityCalculator;
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.delegate.AndroidConstants;
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.fragment.DeleteCustomResolutionDialogBuilder;
import com.robodreamz.density.fragment.OnDeletionSelectedPositionFinder;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.screen.DefaultDensity;
import com.robodreamz.density.screen.ScreenSizeResolver;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class DensityApplication extends Application {

    public static final String TAG = "DensityApplication";
    public static final int DENSITY_APP_DELETE_RESOLUTION_DIALOG = 1000;
    private static final DelegateFactory FACTORY = new DelegateFactory();
    private static final DensityCalculator CALCUALTOR = new DensityCalculator();
    private static final DensitySifter SIFTER = new DensitySifter();
    private static final ScreenSizeResolver RESOLVER = new ScreenSizeResolver();
    private static final Constants CONSTANTS = new AndroidConstants();
    private static final OnDeletionSelectedPositionFinder ON_DELETION_SELECTED_POSITION_FINDER =
            new OnDeletionSelectedPositionFinder(CONSTANTS.getInvalidPositionIndex());
    private static final DeleteCustomResolutionDialogBuilder DELETE_CUSTOM_RESOLUTION_DIALOG_BUILDER = new DeleteCustomResolutionDialogBuilder();

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

    public static Constants getConstants() {
        return CONSTANTS;
    }

    public static OnDeletionSelectedPositionFinder getOnDeleteSelectionFinder() {
        return ON_DELETION_SELECTED_POSITION_FINDER;
    }

    public static DeleteCustomResolutionDialogBuilder getDeleteCustomResolutionDialogBuilder() {
        return DELETE_CUSTOM_RESOLUTION_DIALOG_BUILDER;
    }
}

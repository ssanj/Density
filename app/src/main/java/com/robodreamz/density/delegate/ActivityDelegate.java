/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.app.Activity;

import java.lang.ref.WeakReference;

public class ActivityDelegate implements Delegate<Activity> {

    private final WeakReference<Activity> activityRef;
    private DelegateFactory delegateFactory;

    public ActivityDelegate(final Activity activity, final DelegateFactory delegateFactory) {
        this.activityRef = new WeakReference<Activity>(activity);
        this.delegateFactory = delegateFactory;
    }

    @Override public Activity getDelegate() {
        if (activityRef != null && activityRef.get() != null) {
            return activityRef.get();
        } else {
            return null;
        }
    }

    public ViewDelegate findViewById(int id) {
        if (isNull()) {
            return delegateFactory.createViewDelegate(null);
        } else {
            return delegateFactory.createViewDelegate(activityRef.get().findViewById(id));
        }
    }

    public boolean isNull() {
        return activityRef == null || activityRef.get() == null;
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DelegateFactory {

    private final ViewDelegate viewDelegate;
    private final ContextDelegate contextDelegate;

    public DelegateFactory(final ViewDelegate viewDelegate, final ContextDelegate contextDelegate) {
        this.viewDelegate = viewDelegate;
        this.contextDelegate = contextDelegate;
    }

    public DelegateFactory() {
        viewDelegate = null;
        contextDelegate = null;
    }

    public ViewDelegate createViewDelegate(View view) {
        if (viewDelegate == null) {
            if (view instanceof TextView) {
                return new TextViewDelegate((TextView) view, this);
            } else {
                return new ViewDelegate(view, this);
            }
        } else {
            return viewDelegate;
        }
    }

    public ContextDelegate createContextDelegate(Context context) {
        if (contextDelegate == null) {
            return new ContextDelegate(context, this);
        } else {
            return contextDelegate;
        }
    }
}

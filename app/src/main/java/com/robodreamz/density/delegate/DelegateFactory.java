/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class DelegateFactory {

    public ViewDelegate createViewDelegate(View view) {
        if (view instanceof TextView) {
            return new TextViewDelegate((TextView) view, this);
        } else {
            return new ViewDelegate(view, this);
        }
    }

    public ContextDelegate createContextDelegate(Context context) {
        return new ContextDelegate(context, this);
    }
}

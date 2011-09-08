/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class DelegateFactory {

    public ViewDelegate createViewDelegate(View view) {
        if (view instanceof TextView) {
            return new TextViewDelegate((TextView) view, this);
        } else if (view instanceof SeekBar) {
            return new SeekBarDelegate((SeekBar) view, this);
        } else if (view instanceof ListView) {
            return new ListViewDelegate((ListView) view, this);
        }else {
            return new ViewDelegate(view, this);
        }
    }

    public ContextDelegate createContextDelegate(Context context) {
        return new ContextDelegate(context, this);
    }

    public ActivityDelegate createActivityDelegate(Activity context) {
        return new ActivityDelegate(context, this);
    }
}

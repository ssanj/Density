/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.view.View;

public class ViewDelegate implements Delegate<View> {

    protected final View view;
    protected DelegateFactory delegateFactory;

    public ViewDelegate(final View view, final DelegateFactory delegateFactory) {
        this.view = view;
        this.delegateFactory = delegateFactory;
    }

    @Override public View getDelegate() {
        return view;
    }

    public ViewDelegate findViewById(int id) {
        if (isNull()) {
            return delegateFactory.createViewDelegate(null);
        } else {
            return delegateFactory.createViewDelegate(view.findViewById(id));
        }
    }

    public boolean hasTag(Object tag) {
        return !isNull() && view.getTag() != null && view.getTag().equals(tag);
    }

    public void setTag(Object tag) {
        if (!isNull()) {
            view.setTag(tag);
        }
    }

    public void setBackground(int colour) {
        if (!isNull()) {
            view.setBackgroundColor(colour);
        }
    }

    public boolean isInTouchMode() {
        return !isNull() && (view).isInTouchMode();
    }

    public boolean isNull() {
        return view == null;
    }
}

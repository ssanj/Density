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

    public boolean isNull() {
        return view == null;
    }

    public <T> boolean isOfType(Class<T> clazz) {
        return !isNull() && view.getClass().isAssignableFrom(clazz);
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public final class CustomResolutionElement implements ResolutionItem {

    private ResolutionElement delegate;

    public CustomResolutionElement(final ResolutionElement delegate) {
        this.delegate = delegate;
    }

    @Override public boolean isEnabled() {
        return delegate.isEnabled();
    }

    @Override public ViewType getViewType() {
        return delegate.getViewType();
    }

    @Override public int getElementLayoutId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        return delegate.getView(layoutInflater, view);
    }

    @Override public void check() {
        delegate.check();
    }

    @Override public boolean isChecked() {
        return delegate.isChecked();
    }

    @Override public void uncheck() {
        delegate.uncheck();
    }

    public int getHeight() {
        return delegate.getHeight();
    }

    public int getWidth() {
        return delegate.getWidth();
    }
}

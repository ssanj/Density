/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.view.View;
import android.widget.Toast;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public final class CustomResolutionElement implements ResolutionElement {

    private ResolutionElement delegate;

    public CustomResolutionElement(final ResolutionElement delegate) {
        this.delegate = delegate;
        delegate.setElementLayoutId(R.layout.resolution_list_custom_view);
        delegate.setViewType(ViewType.CUSTOM_ELEMENT);
    }

    @Override public boolean isEnabled() {
        return delegate.isEnabled();
    }

    @Override public ViewType getViewType() {
        return delegate.getViewType();
    }

    @Override public int getElementLayoutId() {
        return delegate.getElementLayoutId();
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

    @Override public void setElementLayoutId(final int layoutId) {
        delegate.setElementLayoutId(layoutId);
    }

    @Override public void setViewType(final ViewType viewType) {
        delegate.setViewType(viewType);
    }

    public int getWidth() {
        return delegate.getWidth();
    }
}

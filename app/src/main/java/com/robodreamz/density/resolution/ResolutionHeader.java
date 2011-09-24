/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public final class ResolutionHeader implements ResolutionItem {

    @Override public boolean isEnabled() {
        return false;
    }

    @Override public ViewType getViewType() {
        return ViewType.HEADER;
    }

    @Override public int getElementLayoutId() {
        return R.layout.resolution_list_header_element;
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        ViewDelegate viewDelegate;
        if (view.isNull() || !view.hasTag(getViewType())) {
            viewDelegate = layoutInflater.inflate(getElementLayoutId());
            viewDelegate.setTag(getViewType());
        } else {
            viewDelegate = view;
        }

        return viewDelegate;
    }

    @Override public void check() { }

    @Override public boolean isChecked() {
        return false;
    }

    @Override public void uncheck() { }

    @Override public void markedForDeletion() { }

    @Override public void unmarkForDeletion() { }
}

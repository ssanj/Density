/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public class ResolutionElement implements ResolutionItem {
    public final int width;
    public final int height;
    private boolean checked;

    public ResolutionElement(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override public boolean isEnabled() {
        return true;
    }

    @Override public ViewType getElementType() {
        return ViewType.ELEMENT;
    }

    @Override public int getElementLayoutId() {
        return R.layout.resolution_list_view;
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        ViewDelegate viewDelegate;
        if (view.isNull() || !view.hasTag(getElementType())) {
            viewDelegate = layoutInflater.inflate(getElementLayoutId());
            viewDelegate.setTag(getElementType());
        } else {
            viewDelegate = view;
        }

        TextViewDelegate widthDelegate = (TextViewDelegate) viewDelegate.findViewById(R.id.resolution_list_view_width);
        TextViewDelegate heightDelegate = (TextViewDelegate) viewDelegate.findViewById(R.id.resolution_list_view_height);
        widthDelegate.setText(String.valueOf(width));
        heightDelegate.setText(String.valueOf(height));
        return viewDelegate;
    }

    public void check() {
        checked = true;
    }

    public boolean isChecked() {
        return checked;
    }

    public void uncheck() {
        checked = false;
    }
}

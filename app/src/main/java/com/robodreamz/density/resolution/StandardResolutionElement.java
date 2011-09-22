/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.graphics.Color;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public class StandardResolutionElement implements ResolutionElement {

    public final int width;
    public final int height;
    private boolean checked;
    private int layoutId = R.layout.resolution_list_standard_view;
    private ViewType viewType = ViewType.ELEMENT;

    public StandardResolutionElement(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override public boolean isEnabled() {
        return true;
    }

    @Override public ViewType getViewType() {
        return viewType;
    }

    @Override public int getElementLayoutId() {
        return layoutId;
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        ViewDelegate viewDelegate;
        if (view.isNull() || !view.hasTag(getViewType())) {
            viewDelegate = layoutInflater.inflate(getElementLayoutId());
            viewDelegate.setTag(getViewType());
        } else {
            viewDelegate = view;
        }

        TextViewDelegate widthDelegate = (TextViewDelegate) viewDelegate.findViewById(R.id.resolution_list_view_width);
        TextViewDelegate heightDelegate = (TextViewDelegate) viewDelegate.findViewById(R.id.resolution_list_view_height);
        widthDelegate.setText(String.valueOf(width));
        heightDelegate.setText(String.valueOf(height));

        if (checked) {
            viewDelegate.setBackground(Color.GREEN);
        } else {
            viewDelegate.setBackground(Color.BLACK);
        }

        return viewDelegate;
    }

    @Override public void check() {
        checked = true;
    }

    @Override public boolean isChecked() {
        return checked;
    }

    @Override public void uncheck() {
        checked = false;
    }

    @Override public int getWidth() {
        return width;
    }

    @Override public int getHeight() {
        return height;
    }

    @Override public void setElementLayoutId(final int layoutId) {
        this.layoutId = layoutId;
    }

    @Override public void setViewType(final ViewType viewType) {
        this.viewType = viewType;
    }
}

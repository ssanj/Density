/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public final class ResolutionElement implements ResolutionItem {
    public int width;
    public int height;

    public ResolutionElement(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override public boolean isEnabled() {
        return true;
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        ViewDelegate viewDelegate;
        if (view.isNull() || !view.hasTag(ViewType.ELEMENT)) {
            viewDelegate = layoutInflater.inflate(R.layout.resolution_list_view);
            viewDelegate.setTag(ViewType.ELEMENT);
        } else {
            viewDelegate = view;
        }

        TextViewDelegate widthDelegate = (TextViewDelegate) viewDelegate.findViewById(R.id.resolution_list_view_width);
        TextViewDelegate heightDelegate = (TextViewDelegate) viewDelegate.findViewById(R.id.resolution_list_view_height);
        widthDelegate.setText(String.valueOf(width));
        heightDelegate.setText(String.valueOf(height));
        return viewDelegate;
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public final class Resolution implements ResolutionItem {
    public int width;
    public int height;

    public static Resolution resolution(int width, int height) {
        final Resolution resolution = new Resolution();
        resolution.width = width;
        resolution.height = height;
        return resolution;
    }

    @Override public boolean isEnabled() {
        return true;
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        ViewDelegate viewDelegate;
        if (view.isNull()) {
            viewDelegate = layoutInflater.inflate(R.layout.resolution_list_view);
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

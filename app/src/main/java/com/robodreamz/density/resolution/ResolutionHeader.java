/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.text.StaticLayout;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public final class ResolutionHeader implements ResolutionItem {

    @Override public boolean isEnabled() {
        return false;
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        ViewDelegate viewDelegate;
        if (view.isNull() || !view.hasTag(ViewType.HEADER)) {
            viewDelegate = layoutInflater.inflate(R.layout.resolution_list_header_element);
            viewDelegate.setTag(ViewType.HEADER);
        } else {
            viewDelegate = view;
        }

        return viewDelegate;
    }
}

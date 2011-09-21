/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public final class CustomResolutionElement implements ResolutionItem {

    private int width;
    private int height;

    public CustomResolutionElement(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    @Override public boolean isEnabled() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public ViewType getElementType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public int getElementLayoutId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public ViewDelegate getView(final LayoutInflaterDelegate layoutInflater, final ViewDelegate view) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public void check() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public boolean isChecked() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override public void uncheck() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

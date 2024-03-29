/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ViewDelegate;

public interface ResolutionItem {

    enum ViewType {ELEMENT, HEADER, CUSTOM_ELEMENT}

    boolean isEnabled();

    ViewType getViewType();

    int getElementLayoutId();

    ViewDelegate getView(LayoutInflaterDelegate layoutInflater, ViewDelegate view);

    void check();

    boolean isChecked();

    void uncheck();

    void markedForDeletion();

    void unmarkForDeletion();
}

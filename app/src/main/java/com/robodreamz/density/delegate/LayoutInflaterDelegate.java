/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.view.LayoutInflater;

public class LayoutInflaterDelegate implements Delegate<LayoutInflater> {

    private final LayoutInflater layoutInflater;
    private DelegateFactory delegateFactory;

    public LayoutInflaterDelegate(final LayoutInflater layoutInflater, final DelegateFactory delegateFactory) {
        this.layoutInflater = layoutInflater;
        this.delegateFactory = delegateFactory;
    }

    public ViewDelegate inflate(int id) {
        return delegateFactory.createViewDelegate(layoutInflater.inflate(id, null));
    }

    @Override public LayoutInflater getDelegate() {
        return layoutInflater;
    }
}

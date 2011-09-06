/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.content.Context;
import android.view.LayoutInflater;

public class ContextDelegate implements Delegate<Context> {

    private Context context;
    private DelegateFactory delegateFactory;

    public ContextDelegate(final Context context, final DelegateFactory delegateFactory) {
        this.context = context;
        this.delegateFactory = delegateFactory;
    }

    public LayoutInflaterDelegate getLayoutInflater() {
        return new LayoutInflaterDelegate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), delegateFactory);
    }

    @Override public Context getDelegate() {
        return context;
    }
}

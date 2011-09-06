/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;

public final class ResolutionListAdapter extends BaseAdapter {

    private ResolutionItem[] resolutions;
    private DelegateFactory delegateFactory;
    private LayoutInflaterDelegate layoutInflater;

    public ResolutionListAdapter(final Context context, final ResolutionItem[] resolutions, final DelegateFactory delegateFactory) {
        this.resolutions = resolutions;
        this.delegateFactory = delegateFactory;
        layoutInflater = delegateFactory.createContextDelegate(context).getLayoutInflater();
    }

    @Override public int getCount() {
        return resolutions.length;
    }

    @Override public Object getItem(final int position) {
        return resolutions[position];
    }

    @Override public long getItemId(final int position) {
        return position;
    }

    @Override public View getView(final int position, final View convertView, final ViewGroup parent) {
        final ResolutionItem resolution = resolutions[position];
        return resolution.getView(layoutInflater, delegateFactory.createViewDelegate(convertView)).getDelegate();
    }

    @Override public boolean isEnabled(final int position) {
        return resolutions[position].isEnabled();
    }
}

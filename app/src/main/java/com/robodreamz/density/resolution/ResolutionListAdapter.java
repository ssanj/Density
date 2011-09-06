/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;

//TODO: Reuse this class by providing a type-annotation.
public final class ResolutionListAdapter extends BaseAdapter {

    private ResolutionItem[] resolutions;
    private DelegateFactory delegateFactory;
    private LayoutInflaterDelegate layoutInflater;

    public ResolutionListAdapter(final LayoutInflaterDelegate layoutInflater, final DelegateFactory delegateFactory,
                                 final ResolutionItem[] resolutions) {
        this.resolutions = resolutions;
        this.delegateFactory = delegateFactory;
        this.layoutInflater = layoutInflater;
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

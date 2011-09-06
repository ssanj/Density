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
import android.widget.TextView;
import com.robodreamz.density.R;

public final class ResolutionListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Resolution[] resolutions;

    public ResolutionListAdapter(final Context context, final Resolution[] resolutions) {
        this.resolutions = resolutions;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.resolution_list_view, null);
        } else { /* view is not null so reuse it. */}

        TextView width = (TextView) view.findViewById(R.id.resolution_list_view_width);
        TextView height = (TextView) view.findViewById(R.id.resolution_list_view_height);

        final Resolution resolution = resolutions[position];
        width.setText(String.valueOf(resolution.width));
        height.setText(String.valueOf(resolution.height));

        return view;
    }
}

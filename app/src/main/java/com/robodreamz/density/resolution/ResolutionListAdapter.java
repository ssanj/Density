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

import static com.robodreamz.density.resolution.Resolution.resolution;

public final class ResolutionListAdapter extends BaseAdapter {

    private static final Resolution[] RESOLUTIONS = {
        resolution(320, 240) /* Sony Xperia x10 mini pro*/,
        resolution(400, 240) /* Samsung Gem*/,
        resolution(480, 320),
        resolution(540, 960),
        resolution(800, 480),
        resolution(854, 480),
        resolution(960, 540),
        resolution(1024, 600) /* Samsung Galaxy Tab*/,
        resolution(1200, 800),
    };

    private LayoutInflater layoutInflater;

    public ResolutionListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override public int getCount() {
        return RESOLUTIONS.length;
    }

    @Override public Object getItem(final int position) {
        return RESOLUTIONS[position];
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

        final Resolution resolution = RESOLUTIONS[position];
        width.setText(String.valueOf(resolution.width));
        height.setText(String.valueOf(resolution.height));

        return view;
    }
}

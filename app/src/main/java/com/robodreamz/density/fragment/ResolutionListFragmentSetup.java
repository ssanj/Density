/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.view.View;
import android.widget.AdapterView;
import com.robodreamz.density.DensityApplication;
import com.robodreamz.density.R;
import com.robodreamz.density.calc.DensityCalculator;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionListAdapter;

public final class ResolutionListFragmentSetup {

    private final ActivityDelegate delegate;


    public ResolutionListFragmentSetup(final ActivityDelegate delegate) {
        this.delegate = delegate;
    }

    public void setup() {
        final ListViewDelegate resolutionList = (ListViewDelegate) delegate.findViewById(R.id.app_screen_resolution_list);
        final DelegateFactory factory = DensityApplication.getFactory();
        final LayoutInflaterDelegate layoutInflater = factory.createContextDelegate(delegate.getDelegate()).getLayoutInflater();
        resolutionList.setAdapter(new ResolutionListAdapter(layoutInflater, factory, ResolutionData.getData()));

        resolutionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                calculateDensity(position, resolutionList);
            }
        });
    }

    private void calculateDensity(final int position, final ListViewDelegate resolutionList) {
        TextViewDelegate value = (TextViewDelegate) delegate.findViewById(R.id.density_result_density_value_text);
        TextViewDelegate category = (TextViewDelegate) delegate.findViewById(R.id.density_result_density_value_category);

        final ResolutionElement item = (ResolutionElement) resolutionList.getAdapter().getItem(position);
        final DensityCalculator.DensityCaluclation caluclation =
                DensityApplication.getCalcualtor().getDensityFor(item.width, item.height, DensityApplication.getResolver().getScreenDiagonal(delegate));

        if (caluclation.isValid()) {
            value.setText(String.valueOf(caluclation.getResult()));
            category.setText(DensityApplication.getSifter().sift(caluclation.getResult()).toString());
        } else {
            delegate.makeLongToast("Invalid values chosen for screen size and/or resolution.");
        }
    }
}

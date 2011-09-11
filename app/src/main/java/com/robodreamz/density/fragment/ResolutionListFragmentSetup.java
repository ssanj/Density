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
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DensityResultCalculator;
import com.robodreamz.density.screen.ScreenSizeResolver;

public final class ResolutionListFragmentSetup {

    private final ActivityDelegate delegate;


    public ResolutionListFragmentSetup(final ActivityDelegate delegate) {
        this.delegate = delegate;
    }

    public void setup() {
        final ListViewDelegate resolutionList = (ListViewDelegate) delegate.findViewById(R.id.app_screen_resolution_list);
        final DelegateFactory factory = DensityApplication.getFactory();
        final LayoutInflaterDelegate layoutInflater = delegate.getLayoutInflater();
        final DensityResultCalculator densityResultCalculator =
                new DensityResultCalculator(
                        delegate,
                        DensityApplication.getCalcualtor(),
                        DensityApplication.getResolver(),
                        DensityApplication.getSifter());

        resolutionList.setAdapter(new ResolutionListAdapter(layoutInflater, factory, ResolutionData.getData()));
        resolutionList.setOnItemClickListener(new ResolutionListClickListener(densityResultCalculator, resolutionList));
    }

    final static class ResolutionListClickListener implements AdapterView.OnItemClickListener {

        private final DensityResultCalculator densityResultCalculator;
        private final ListViewDelegate resolutionList;


        public ResolutionListClickListener(DensityResultCalculator densityResultCalculator, final ListViewDelegate resolutionList) {
            this.densityResultCalculator = densityResultCalculator;
            this.resolutionList = resolutionList;
        }

        @Override public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            doOnItemClick(position);
        }

        void doOnItemClick(final int position) {
            densityResultCalculator.calculateDensity(position, resolutionList);
        }
    }
}

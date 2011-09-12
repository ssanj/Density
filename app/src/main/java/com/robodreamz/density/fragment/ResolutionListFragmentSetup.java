/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.view.View;
import android.widget.AdapterView;
import com.robodreamz.density.DensityApplication;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DefaultDensity;
import com.robodreamz.density.screen.DensityResultCalculator;

public final class ResolutionListFragmentSetup {

    private final ActivityDelegate delegate;
    private DensityResultCalculator densityResultCalculator;
    private DefaultDensity defaultDensity;


    public ResolutionListFragmentSetup(final ActivityDelegate delegate, final DensityResultCalculator densityResultCalculator,
                                       final DefaultDensity defaultDensity) {
        this.delegate = delegate;
        this.densityResultCalculator = densityResultCalculator;
        this.defaultDensity = defaultDensity;
    }

    public void setup() {
        final ListViewDelegate resolutionList = (ListViewDelegate) delegate.findViewById(R.id.app_screen_resolution_list);
        final DelegateFactory factory = DensityApplication.getFactory();
        final LayoutInflaterDelegate layoutInflater = delegate.getLayoutInflater();

        resolutionList.setAdapter(new ResolutionListAdapter(layoutInflater, factory, ResolutionData.getData()));
        resolutionList.setOnItemClickListener(new ResolutionListClickListener(densityResultCalculator, resolutionList));
        resolutionList.setOnItemSelectedListener(new ResolutionListSelectListener(resolutionList, densityResultCalculator, defaultDensity));
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

    static class ResolutionListSelectListener implements AdapterView.OnItemSelectedListener  {

        private final ListViewDelegate listView;
        private DensityResultCalculator calculator;
        private final DefaultDensity defaultDensity;

        public ResolutionListSelectListener(final ListViewDelegate listView, final DensityResultCalculator calculator,
                                            final DefaultDensity defaultDensity) {
            this.listView = listView;
            this.calculator = calculator;
            this.defaultDensity = defaultDensity;
        }

        @Override public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
            doOnItemSelected(position);
        }

        @Override public void onNothingSelected(final AdapterView<?> parent) {
            doOnNothingSelected();
        }

        public void doOnNothingSelected() {
            defaultDensity.setValue();
        }

        public void doOnItemSelected(final int position) {
            calculator.calculateDensity(position, listView);
        }
    }
}

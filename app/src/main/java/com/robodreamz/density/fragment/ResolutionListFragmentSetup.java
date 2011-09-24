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
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ClickableItems;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionItem;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DensityResultCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ResolutionListFragmentSetup {

    private final ActivityDelegate delegate;
    private DensityResultCalculator densityResultCalculator;


    public ResolutionListFragmentSetup(final ActivityDelegate delegate, final DensityResultCalculator densityResultCalculator) {
        this.delegate = delegate;
        this.densityResultCalculator = densityResultCalculator;
    }

    //TODO: Pass all the instances in through this method.
    public void setup() {
        final ListViewDelegate resolutionList = (ListViewDelegate) delegate.findViewById(R.id.app_screen_resolution_list);
        final DelegateFactory factory = DensityApplication.getFactory();
        final LayoutInflaterDelegate layoutInflater = delegate.getLayoutInflater();

        resolutionList.setAdapter(new ResolutionListAdapter(layoutInflater, factory, getResolutionItems(),
                DensityApplication.getConstants()));
        resolutionList.setOnItemClickListener(new ResolutionListClickListener(densityResultCalculator, resolutionList));
        resolutionList.setOnItemSelectedListener(new ResolutionListSelectListener(resolutionList, densityResultCalculator));
        resolutionList.setOnItemLongClickListener(new ResolutionListLongClickListener(resolutionList, delegate));
    }

    List<ResolutionItem> getResolutionItems() {
        final List<ResolutionItem> resolutionItems = new ArrayList<ResolutionItem>();
        resolutionItems.addAll(Arrays.asList(ResolutionData.getData()));
        //TODO: later we would merge custom and standard resolutions here.
        return resolutionItems;
    }

    public void onResume(final Constants constants) {
        final ListViewDelegate resolutionList = (ListViewDelegate) delegate.findViewById(R.id.app_screen_resolution_list);
        final int currentIndex = ResolutionData.INDEX_PAIR.getCurrentIndex();
        if (!constants.isInvalidPosition(currentIndex)) {
            resolutionList.setSelection(currentIndex); //set the selection for track mode.
            ((ClickableItems) resolutionList.getAdapter()).clickedItem(currentIndex); //set the click index for touch mode.

            if (resolutionList.isInTouchMode()) {//in touch mode the density result text is not updated automatically.
                densityResultCalculator.calculateDensity(currentIndex, resolutionList);
            } else { /*  density result text is updated automatically for track mode.*/ }
        }
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

        public ResolutionListSelectListener(final ListViewDelegate listView, final DensityResultCalculator calculator) {
            this.listView = listView;
            this.calculator = calculator;
        }

        @Override public void onItemSelected(final AdapterView<?> parent, final View view, final int position, final long id) {
            doOnItemSelected(position);
        }

        @Override public void onNothingSelected(final AdapterView<?> parent) {
            doOnNothingSelected();
        }

        public void doOnNothingSelected() { /* do nothing */ }

        public void doOnItemSelected(final int position) {
            calculator.calculateDensity(position, listView);
        }
    }

    static class ResolutionListLongClickListener implements AdapterView.OnItemLongClickListener {

        private ListViewDelegate resolutionList;
        private ActivityDelegate delegate;

        public ResolutionListLongClickListener(final ListViewDelegate resolutionList, final ActivityDelegate delegate) {
            this.resolutionList = resolutionList;
            this.delegate = delegate;
        }

        @Override public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            final ResolutionListAdapter adapter = (ResolutionListAdapter) resolutionList.getAdapter();
            final ResolutionElement element = (ResolutionElement) adapter.getItem(position);
            if (element.getViewType() == ResolutionItem.ViewType.CUSTOM_ELEMENT) {
                ResolutionData.DELETION_INDEX.set(position);
                adapter.markForDeletion(position);
                delegate.showDialog(DensityApplication.DENSITY_APP_DELETE_RESOLUTION_DIALOG);
            }
            return true;
        }
    }
}

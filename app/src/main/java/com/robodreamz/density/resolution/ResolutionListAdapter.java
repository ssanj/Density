/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;

import java.util.ArrayList;
import java.util.List;

//TODO: Reuse this class by providing a type-annotation.
public class ResolutionListAdapter extends BaseAdapter implements ClickableItems {

    private List<ResolutionItem> resolutions;
    private DelegateFactory delegateFactory;
    private LayoutInflaterDelegate layoutInflater;
    private Constants constants;

    public ResolutionListAdapter(final LayoutInflaterDelegate layoutInflater, final DelegateFactory delegateFactory,
                                 final List<ResolutionItem> resolutions, final Constants constants) {
        this.resolutions = new ArrayList<ResolutionItem>(resolutions);
        this.delegateFactory = delegateFactory;
        this.layoutInflater = layoutInflater;
        this.constants = constants;
    }

    @Override public int getCount() {
        return resolutions.size();
    }

    @Override public Object getItem(final int position) {
        return resolutions.get(position);
    }

    @Override public long getItemId(final int position) {
        return position;
    }

    @Override public boolean areAllItemsEnabled() {
        return false;
    }

    @Override public View getView(final int position, final View convertView, final ViewGroup parent) {
        final ResolutionItem resolution = resolutions.get(position);
        return resolution.getView(layoutInflater, delegateFactory.createViewDelegate(convertView)).getDelegate();
    }

    public void clickedItem(int position) {
        final IndexPair indexPair = ResolutionData.INDEX_PAIR;
        if (indexPair.isNew(position)) {
            final int currentIndex = indexPair.getCurrentIndex();
            if (!constants.isInvalidPosition(currentIndex)) {
                resolutions.get(currentIndex).uncheck();
            }

            resolutions.get(position).check();
            indexPair.update(position);
            notifyDataSetChanged();
        }
    }

    public void resetClick() {
        final IndexPair indexPair = ResolutionData.INDEX_PAIR;
        final int currentIndex = indexPair.getCurrentIndex();
        if (!constants.isInvalidPosition(currentIndex)) {
            resolutions.get(currentIndex).uncheck();
            indexPair.update(constants.getInvalidPositionIndex());
            notifyDataSetChanged();
        }
    }

    public void resetState() {
        for (ResolutionItem item : resolutions) {
            item.uncheck();
        }

        notifyDataSetChanged();
    }

    @Override public boolean isEnabled(final int position) {
        return resolutions.get(position).isEnabled();
    }

    public void removeItem(final int position) {
        if (resolutions.get(position).getViewType() == ResolutionItem.ViewType.CUSTOM_ELEMENT) {
            resolutions.remove(position);
            //once we remove the current selection, invalid the selected index.
            ResolutionData.INDEX_PAIR.update(constants.getInvalidPositionIndex());
            notifyDataSetChanged();
        }
    }

    public void markForDeletion(final int position) {
        if (resolutions.get(position).getViewType() == ResolutionItem.ViewType.CUSTOM_ELEMENT) {
            resolutions.get(position).markedForDeletion();
            notifyDataSetChanged();
        }
    }

    public void unmarkForDeletion(final int position) {
        if (resolutions.get(position).getViewType() == ResolutionItem.ViewType.CUSTOM_ELEMENT) {
            resolutions.get(position).unmarkForDeletion();
            notifyDataSetChanged();
        }
    }
}

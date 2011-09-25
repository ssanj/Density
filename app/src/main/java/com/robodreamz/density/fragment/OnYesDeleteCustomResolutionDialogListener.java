/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.content.DialogInterface;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.DeletionItemIndex;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionListAdapter;

public final class OnYesDeleteCustomResolutionDialogListener implements DialogInterface.OnClickListener {

    private ListViewDelegate resolutionList;
    private ResolutionListAdapter adapter;
    private OnDeletionSelectedPositionFinder finder;

    public OnYesDeleteCustomResolutionDialogListener(final ListViewDelegate resolutionList, final ResolutionListAdapter adapter,
                                                     final OnDeletionSelectedPositionFinder finder) {

        this.resolutionList = resolutionList;
        this.adapter = adapter;
        this.finder = finder;
    }

    @Override public void onClick(final DialogInterface dialog, final int which) {
        final DeletionItemIndex deletionIndex = ResolutionData.DELETION_INDEX;
        if (deletionIndex.isValid()) {
            final int positionToDelete = deletionIndex.getValue();
            adapter.removeItem(positionToDelete);
            final int listSizeAfterDeletion = adapter.getCount();
            final OnDeletionSelectedPositionFinder.OnDeletionSelectedPosition newPositionToSelect =
                    finder.findSelectionPosition(listSizeAfterDeletion, positionToDelete);
            adapter.resetState();

            if (newPositionToSelect.isValid()) {
                final int newPosition = newPositionToSelect.getPosition();
                resolutionList.performItemClick(newPosition, newPosition);
            } else {
                ResolutionData.INDEX_PAIR.resetCurrentIndex();
            }
        }

        ResolutionData.DELETION_INDEX.reset();
        dialog.dismiss();

    }
}

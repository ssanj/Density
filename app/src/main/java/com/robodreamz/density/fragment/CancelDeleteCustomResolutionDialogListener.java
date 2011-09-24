/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.content.DialogInterface;
import com.robodreamz.density.resolution.ResolutionListAdapter;

public final class CancelDeleteCustomResolutionDialogListener implements DialogInterface.OnClickListener {

    private ResolutionListAdapter adapter;
    private int position;

    public CancelDeleteCustomResolutionDialogListener(final ResolutionListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override public void onClick(final DialogInterface dialog, final int which) {
        adapter.unmarkForDeletion(position);
        dialog.dismiss();
    }

    //This method must be called from prepareDialog with the correct position.
    public void setPosition(final int position) {
        this.position = position;
    }
}

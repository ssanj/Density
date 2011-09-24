/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.content.DialogInterface;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionListAdapter;

public final class CancelDeleteCustomResolutionDialogListener implements DialogInterface.OnClickListener {

    private ResolutionListAdapter adapter;

    public CancelDeleteCustomResolutionDialogListener(final ResolutionListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override public void onClick(final DialogInterface dialog, final int which) {
        adapter.unmarkForDeletion(ResolutionData.DELETION_INDEX.get());
        dialog.dismiss();
    }
}

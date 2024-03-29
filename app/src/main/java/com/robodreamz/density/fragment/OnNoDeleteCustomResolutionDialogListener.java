/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.content.DialogInterface;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionListAdapter;

public final class OnNoDeleteCustomResolutionDialogListener implements DialogInterface.OnClickListener {

    private ResolutionListAdapter adapter;

    public OnNoDeleteCustomResolutionDialogListener(final ResolutionListAdapter adapter) {
        this.adapter = adapter;
    }

    @Override public void onClick(final DialogInterface dialog, final int which) {
        adapter.unmarkForDeletion(ResolutionData.DELETION_INDEX.getValue());
        dialog.dismiss();
    }
}

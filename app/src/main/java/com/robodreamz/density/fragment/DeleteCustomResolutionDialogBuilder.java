/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import com.robodreamz.density.R;
import com.robodreamz.density.resolution.ResolutionElement;

public final class DeleteCustomResolutionDialogBuilder {

    public AlertDialog create(final Context context, final ResolutionElement element,
                              final DialogInterface.OnClickListener onYes, final DialogInterface.OnClickListener onNo) {
        return new AlertDialog.Builder(context).
                setTitle(context.getString(R.string.delete_resolution_title)).
                setMessage(createResolutionText(element)).
                setPositiveButton(context.getString(R.string.yes_button_title), onYes).
                setNegativeButton(context.getString(R.string.no_button_title), onNo).
                create();

    }

    public String createResolutionText(final ResolutionElement element) {
        return new StringBuilder("Delete Resolution ").append(element.getWidth()).append("x").append(element.getHeight()).append(" ?").toString();
    }
}

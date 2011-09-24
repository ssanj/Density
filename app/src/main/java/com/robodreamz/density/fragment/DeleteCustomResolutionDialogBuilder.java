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

    private Context context;
    private ResolutionElement element;

    public DeleteCustomResolutionDialogBuilder(final Context context, final ResolutionElement element)  {
        this.context = context;
        this.element = element;
    }

    public AlertDialog create() {
        return new AlertDialog.Builder(context).
                setTitle(context.getString(R.string.delete_resolution_title)).
                setMessage(createResolution(element)).
                setPositiveButton(context.getString(R.string.yes_button_title), new DialogInterface.OnClickListener() {
                    @Override public void onClick(final DialogInterface dialog, final int which) {

                    }
                }).
                setNegativeButton(context.getString(R.string.no_button_title), new DialogInterface.OnClickListener() {
                    @Override public void onClick(final DialogInterface dialog, final int which) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                }).
                create();
    }

    private String createResolution(final ResolutionElement element) {
        return new StringBuilder("Delete Resolution ").append(element.getWidth()).append("x").append(element.getHeight()).append(" ?").toString();
    }
}

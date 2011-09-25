/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewDelegate extends ViewDelegate {

    public ListViewDelegate(final ListView view, final DelegateFactory delegateFactory) {
        super(view, delegateFactory);
    }

    public void setAdapter(ListAdapter adapter) {
        if (!isNull()) {
            ((ListView) view).setAdapter(adapter);
        }
    }

    public ListAdapter getAdapter() {
        if (!isNull()) {
            return ((ListView) view).getAdapter();
        } else {
            return null;
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        if (!isNull()) {
            ((ListView) view).setOnItemClickListener(listener);
        }
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        if (!isNull()) {
            ((ListView) view).setOnItemSelectedListener(listener);
        }
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        if (!isNull()) {
            ((ListView) view).setOnItemLongClickListener(listener);
        }
    }

    public void setSelection(int position) {
        if (!isNull()) {
            ((ListView) view).setSelection(position);
        }
    }

    public void performItemClick(int position, int id) {
        if (!isNull()) {
             ((ListView) view).performItemClick(null, position, id);
        }
    }
}

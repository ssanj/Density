/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.widget.AdapterView;

public final class AndroidConstants implements Constants {

    @Override public int getInvalidPositionIndex() {
        return AdapterView.INVALID_POSITION;
    }

    @Override public boolean isInvalidPosition(final int position) {
        return AdapterView.INVALID_POSITION == position;
    }
}

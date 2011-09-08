/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.widget.SeekBar;

public final class SeekBarDelegate extends ViewDelegate {

    public SeekBarDelegate(final SeekBar seekBar, final DelegateFactory delegateFactory) {
        super(seekBar, delegateFactory);
    }

    public void setProgress(int progress) {
        if (!isNull()) {
            ((SeekBar) view).setProgress(progress);
        }
    }

    public int getProgress() {
        if (!isNull()) {
            return ((SeekBar) view).getProgress();
        } else {
            return 0;
        }
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener l) {
        if (!isNull()) {
            ((SeekBar) view).setOnSeekBarChangeListener(l);
        }
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.widget.SeekBar;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.SeekBarDelegate;
import com.robodreamz.density.screen.ScreenSizeResolver;

public abstract class DefaultSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private final DelegateFactory delegateFactory;

    public DefaultSeekBarChangeListener(final DelegateFactory delegateFactory) {
        this.delegateFactory = delegateFactory;
    }

    @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
        onProgressChanged(DensityApplication.getResolver(),(SeekBarDelegate) delegateFactory.createViewDelegate(seekBar), progress, fromUser);
    }

    public abstract void onProgressChanged(ScreenSizeResolver resolver, SeekBarDelegate seekBar, int progress, boolean fromUser);

    @Override public void onStartTrackingTouch(final SeekBar seekBar) { }

    @Override public void onStopTrackingTouch(final SeekBar seekBar) { }
}
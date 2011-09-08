/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.widget.SeekBar;
import com.robodreamz.density.DefaultSeekBarChangeListener;
import com.robodreamz.density.DensityApplication;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.SeekBarDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;

public final class ScreenSizeFragmentSetup {

    private final ActivityDelegate delegate;

    public ScreenSizeFragmentSetup(final ActivityDelegate delegate) {
        this.delegate = delegate;
    }

    public void setup() {
        final TextViewDelegate screenSizeTextView = (TextViewDelegate) delegate.findViewById(R.id.app_screen_screensize_value_text);
        final SeekBarDelegate integerBar = (SeekBarDelegate) delegate.findViewById(R.id.app_screen_screensize_integer_progress);
        final SeekBarDelegate decimalBar = (SeekBarDelegate) delegate.findViewById(R.id.app_screen_screensize_decimal_progress);

        integerBar.setOnSeekBarChangeListener(new DefaultSeekBarChangeListener() {
            @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                screenSizeTextView.setText(DensityApplication.getResolver().convertProgressValueToActualString(progress, decimalBar.getProgress()));
            }
        });

        decimalBar.setOnSeekBarChangeListener(new DefaultSeekBarChangeListener() {
            @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                screenSizeTextView.setText(DensityApplication.getResolver().convertProgressValueToActualString(integerBar.getProgress(), progress));
            }
        });

        setInitialProgress(integerBar, decimalBar);
    }

    private void setInitialProgress(final SeekBarDelegate integerBar, final SeekBarDelegate decimalBar) {
        integerBar.setProgress(DensityApplication.getResolver().convertActualValueToProgressInt(2));
        decimalBar.setProgress(5); //.5
    }
}

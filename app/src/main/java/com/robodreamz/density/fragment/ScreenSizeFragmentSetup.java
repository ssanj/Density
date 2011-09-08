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

        integerBar.setOnSeekBarChangeListener(new IntegralBarChangeListener(decimalBar, screenSizeTextView));
        decimalBar.setOnSeekBarChangeListener(new DecimalBarChangeListener(integerBar, screenSizeTextView));

        setInitialProgress(integerBar, decimalBar);
    }

    private void setInitialProgress(final SeekBarDelegate integerBar, final SeekBarDelegate decimalBar) {
        integerBar.setProgress(DensityApplication.getResolver().convertActualValueToProgressInt(2));
        decimalBar.setProgress(5); //.5
    }

    static final class IntegralBarChangeListener extends DefaultSeekBarChangeListener {
        private final SeekBarDelegate decimalBar;
        private final TextViewDelegate screenSizeValue;

        public IntegralBarChangeListener(final SeekBarDelegate decimalBar, final TextViewDelegate screenSizeValue) {
            this.decimalBar = decimalBar;
            this.screenSizeValue = screenSizeValue;
        }

        @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
            screenSizeValue.setText(DensityApplication.getResolver().convertProgressValueToActualString(progress, decimalBar.getProgress()));
        }
    }

    static final class DecimalBarChangeListener extends DefaultSeekBarChangeListener {
        private final SeekBarDelegate integralBar;
        private final TextViewDelegate screenSizeValue;

        DecimalBarChangeListener(final SeekBarDelegate integralBar, final TextViewDelegate screenSizeValue) {
            this.integralBar = integralBar;
            this.screenSizeValue = screenSizeValue;
        }

        @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
            screenSizeValue.setText(DensityApplication.getResolver().convertProgressValueToActualString(integralBar.getProgress(), progress));
        }
    }
}

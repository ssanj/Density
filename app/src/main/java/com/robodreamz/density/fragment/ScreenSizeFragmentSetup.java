/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import com.robodreamz.density.DefaultSeekBarChangeListener;
import com.robodreamz.density.DensityApplication;
import com.robodreamz.density.R;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.delegate.SeekBarDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.screen.DensityResultCalculator;
import com.robodreamz.density.screen.ScreenSizeResolver;

public final class ScreenSizeFragmentSetup {

    private final ActivityDelegate delegate;
    private DelegateFactory delegateFactory;

    public ScreenSizeFragmentSetup(final ActivityDelegate delegate, final DelegateFactory delegateFactory) {
        this.delegate = delegate;
        this.delegateFactory = delegateFactory;
    }

    public void setup(final DensityResultCalculator densityResultCalculator) {
        final TextViewDelegate screenSizeTextView = (TextViewDelegate) delegate.findViewById(R.id.app_screen_screensize_value_text);
        final SeekBarDelegate integerBar = (SeekBarDelegate) delegate.findViewById(R.id.app_screen_screensize_integer_progress);
        final SeekBarDelegate decimalBar = (SeekBarDelegate) delegate.findViewById(R.id.app_screen_screensize_decimal_progress);
        final ListViewDelegate resolutionList = (ListViewDelegate) delegate.findViewById(R.id.app_screen_resolution_list);

        integerBar.setOnSeekBarChangeListener(new IntegralBarChangeListener(decimalBar, screenSizeTextView, densityResultCalculator,
                resolutionList, delegateFactory));
        decimalBar.setOnSeekBarChangeListener(new DecimalBarChangeListener(integerBar, screenSizeTextView, densityResultCalculator,
                resolutionList, delegateFactory));

        setInitialProgress(integerBar, decimalBar);
    }

    private void setInitialProgress(final SeekBarDelegate integerBar, final SeekBarDelegate decimalBar) {
        integerBar.setProgress(DensityApplication.getResolver().convertActualValueToProgressInt(2));
        decimalBar.setProgress(5); //.5
    }

    static final class IntegralBarChangeListener extends DefaultSeekBarChangeListener {
        private final SeekBarDelegate decimalBar;
        private final TextViewDelegate screenSizeValue;
        private final DensityResultCalculator densityResultCalculator;
        private final ListViewDelegate resolutionList;

        public IntegralBarChangeListener(final SeekBarDelegate decimalBar, final TextViewDelegate screenSizeValue,
                                         final DensityResultCalculator densityResultCalculator,
                                         final ListViewDelegate resolutionList, final DelegateFactory delegateFactory) {
            super(delegateFactory);
            this.decimalBar = decimalBar;
            this.screenSizeValue = screenSizeValue;
            this.densityResultCalculator = densityResultCalculator;
            this.resolutionList = resolutionList;
        }

        @Override public void onProgressChanged(final ScreenSizeResolver resolver, final SeekBarDelegate seekBar, final int progress,
                                                final boolean fromUser) {
            screenSizeValue.setText(resolver.convertProgressValueToActualString(progress, decimalBar.getProgress()));
            densityResultCalculator.calculateDensity(resolutionList);
        }
    }

    static final class DecimalBarChangeListener extends DefaultSeekBarChangeListener {
        private final SeekBarDelegate integralBar;
        private final TextViewDelegate screenSizeValue;
        private final DensityResultCalculator densityResultCalculator;
        private final ListViewDelegate resolutionList;

        DecimalBarChangeListener(final SeekBarDelegate integralBar, final TextViewDelegate screenSizeValue,
                                 final DensityResultCalculator densityResultCalculator, final ListViewDelegate resolutionList,
                                 final DelegateFactory delegateFactory) {
            super(delegateFactory);
            this.integralBar = integralBar;
            this.screenSizeValue = screenSizeValue;
            this.densityResultCalculator = densityResultCalculator;
            this.resolutionList = resolutionList;
        }

        @Override public void onProgressChanged(final ScreenSizeResolver resolver, final SeekBarDelegate seekBar, final int progress,
                                                final boolean fromUser) {
            screenSizeValue.setText(resolver.convertProgressValueToActualString(integralBar.getProgress(), progress));
            densityResultCalculator.calculateDensity(resolutionList);
        }
    }
}

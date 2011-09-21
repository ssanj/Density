/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

import com.robodreamz.density.R;
import com.robodreamz.density.calc.DensityCalculator;
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.StandardResolutionElement;
import com.robodreamz.density.resolution.ClickableItems;

public class DensityResultCalculator {

    private final ActivityDelegate delegate;
    private final DensityCalculator calculator;
    private final ScreenSizeResolver resolver;
    private final DensitySifter sifter;
    private Constants constants;

    public DensityResultCalculator(final ActivityDelegate delegate,
                                   final DensityCalculator calculator,
                                   final ScreenSizeResolver resolver,
                                   final DensitySifter sifter,
                                   final Constants constants) {
        this.delegate = delegate;
        this.calculator = calculator;
        this.resolver = resolver;
        this.sifter = sifter;
        this.constants = constants;
    }

    public void calculateDensity(final int position, final ListViewDelegate resolutionList) {
        TextViewDelegate value = (TextViewDelegate) delegate.findViewById(R.id.density_result_density_value_text);
        TextViewDelegate category = (TextViewDelegate) delegate.findViewById(R.id.density_result_density_value_category);

        final StandardResolutionElement item = (StandardResolutionElement) resolutionList.getAdapter().getItem(position);
        final DensityCalculator.DensityCaluclation calculation =
                calculator.getDensityFor(item.width, item.height, resolver.getScreenDiagonal(delegate));

        if (calculation.isValid()) {
            value.setText(String.valueOf(calculation.getResult()));
            category.setText(sifter.sift(calculation.getResult()).toString());
            ((ClickableItems) resolutionList.getAdapter()).clickedItem(position);
        } else {
            delegate.makeLongToast("Invalid values chosen for screen size and/or resolution.");
        }
    }

    public void calculateDensity(final ListViewDelegate resolutionList) {
        final int currentIndex = ResolutionData.INDEX_PAIR.getCurrentIndex();
        if (!constants.isInvalidPosition(currentIndex)) {
             calculateDensity(currentIndex, resolutionList);
         }
    }

}

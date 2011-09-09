/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

import android.widget.ListAdapter;
import com.robodreamz.density.R;
import com.robodreamz.density.calc.DensityCalculator;
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.screen.ScreenSizeResolver;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public final class ResolutionListClickListenerTest {

    private static final String CALC_ERROR = "Your inputs are crazy!";

    private ActivityDelegate mockActivity;
    private DensityCalculator mockCalculator;
    private ScreenSizeResolver mockResolver;
    private DensitySifter mockSifter;
    private ListViewDelegate mockListView;
    private TextViewDelegate mockDensityValue;
    private TextViewDelegate mockDensityCategory;
    private DensityCalculator.DensityCaluclation mockCalculation;

    private ListAdapter mockListAdapter;
    private final int itemPosition = 100;
    private final int density = 280;
    private final double screenSize = 3.5;
    private final int width = 320;
    private final int height = 240;
    private final DensitySifter.DPI densityCategory = DensitySifter.DPI.XHDPI;

    @Before public void setup() throws Exception {
        mockActivity = mock(ActivityDelegate.class);

        mockCalculator = mock(DensityCalculator.class);
        mockResolver = mock(ScreenSizeResolver.class);
        mockSifter = mock(DensitySifter.class);
        mockListView = mock(ListViewDelegate.class);
        mockDensityValue = mock(TextViewDelegate.class);
        mockDensityCategory = mock(TextViewDelegate.class);
        mockCalculation = mock(DensityCalculator.DensityCaluclation.class);
        mockListAdapter = mock(ListAdapter.class);
    }

    @Test public void shouldUpdateDensityTextWhenAListItemIsClicked() {
        assertDensityWidgets();
        assertListAdapter();
        assertScreenSize();
        assertDensityValue();
        assertDensityCategory();

        final ResolutionListFragmentSetup.ResolutionListClickListener listener =
                new ResolutionListFragmentSetup.ResolutionListClickListener(mockActivity, mockListView, mockCalculator, mockResolver, mockSifter);

        listener.calculateDensity(itemPosition, mockListView);

        verify(mockCalculation).isValid();
        verify(mockDensityValue).setText(String.valueOf(density));
        verify(mockDensityCategory).setText(densityCategory.toString());
    }

    @Test public void shouldToastWhenCalculationIsInvalid() {
        assertDensityWidgets();
        assertListAdapter();
        assertScreenSize();
        assertDensityValueWithInvalidCalculation();

        final ResolutionListFragmentSetup.ResolutionListClickListener listener =
                new ResolutionListFragmentSetup.ResolutionListClickListener(mockActivity, mockListView, mockCalculator, mockResolver, mockSifter);

        listener.calculateDensity(itemPosition, mockListView);

        verify(mockCalculation).isValid();
        verify(mockActivity).makeLongToast("Invalid values chosen for screen size and/or resolution.");
        verifyZeroInteractions(mockDensityValue);
        verifyZeroInteractions(mockDensityCategory);
    }

    private void assertDensityWidgets() {
        when(mockActivity.findViewById(R.id.density_result_density_value_text)).thenReturn(mockDensityValue);
        when(mockActivity.findViewById(R.id.density_result_density_value_category)).thenReturn(mockDensityCategory);
    }

    private void assertListAdapter() {
        when(mockListView.getAdapter()).thenReturn(mockListAdapter);
        when(mockListAdapter.getItem(itemPosition)).thenReturn(new ResolutionElement(width, height));
    }

    private void assertScreenSize() {
        when(mockResolver.getScreenDiagonal(mockActivity)).thenReturn(screenSize);
    }

    private void assertDensityCategory() {
        when(mockSifter.sift(density)).thenReturn(densityCategory);
    }

    private void assertDensityValue() {
        when(mockCalculator.getDensityFor(width, height, screenSize)).thenReturn(mockCalculation);
        when(mockCalculation.isValid()).thenReturn(true);
        when(mockCalculation.getResult()).thenReturn(density);
    }

    private void assertDensityValueWithInvalidCalculation() {
        when(mockCalculator.getDensityFor(width, height, screenSize)).thenReturn(mockCalculation);
        when(mockCalculation.isValid()).thenReturn(false);
        when(mockCalculation.getError()).thenReturn(CALC_ERROR);
    }

}

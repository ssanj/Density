/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionListAdapter;

public final class DensityAppActivity extends AbstractDensityActivty {

    private static final int INTEGRAL_SCREENSIZE_OFFSET = 2;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_screen);
        initScreenSize();
        initResolutions();
    }

    private void initScreenSize() {
        final TextView screenSizeTextView = (TextView) findViewById(R.id.app_screen_screensize_value_text);
        final SeekBar integerBar = (SeekBar) findViewById(R.id.app_screen_screensize_integer_progress);
        final SeekBar decimalBar = (SeekBar) findViewById(R.id.app_screen_screensize_decimal_progress);

        integerBar.setOnSeekBarChangeListener(new DefaultSeekBarChangeListener() {
            @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                screenSizeTextView.setText(getScreenSizeText(progress, decimalBar.getProgress()));
            }
        });

        decimalBar.setOnSeekBarChangeListener(new DefaultSeekBarChangeListener() {
            @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                screenSizeTextView.setText(getScreenSizeText(integerBar.getProgress(), progress));
            }
        });

        setInitialProgress(integerBar, decimalBar);
    }

    private void initResolutions() {
        final ListView resolutionList = (ListView) findViewById(R.id.app_screen_resolution_list);
        resolutionList.setAdapter(new ResolutionListAdapter(this, ResolutionData.getData(), new DelegateFactory()));
    }

    private void setInitialProgress(final SeekBar integerBar, final SeekBar decimalBar) {
        integerBar.setProgress(0); //2
        decimalBar.setProgress(5); //.5
    }

    private CharSequence getScreenSizeText(final int integral, final int decimal) {
        return new StringBuilder(String.valueOf(INTEGRAL_SCREENSIZE_OFFSET + integral)).append('.').append(decimal);
    }

    private static class DefaultSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) { }

        @Override public void onStartTrackingTouch(final SeekBar seekBar) { }

        @Override public void onStopTrackingTouch(final SeekBar seekBar) { }
    }
}

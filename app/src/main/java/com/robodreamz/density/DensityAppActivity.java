/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.robodreamz.density.calc.DensityCalculator;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.LayoutInflaterDelegate;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionListAdapter;

//TODO: This class does too much. Refactor into separate classes.
public final class DensityAppActivity extends AbstractDensityActivty {

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
                screenSizeTextView.setText(DensityApplication.getResolver().resolve(progress, decimalBar.getProgress()));
            }
        });

        decimalBar.setOnSeekBarChangeListener(new DefaultSeekBarChangeListener() {
            @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
                screenSizeTextView.setText(DensityApplication.getResolver().resolve(integerBar.getProgress(), progress));
            }
        });

        setInitialProgress(integerBar, decimalBar);
    }

    private void initResolutions() {
        final ListView resolutionList = (ListView) findViewById(R.id.app_screen_resolution_list);
        final DelegateFactory factory = DensityApplication.getFactory();
        final LayoutInflaterDelegate layoutInflater = factory.createContextDelegate(this).getLayoutInflater();
        resolutionList.setAdapter(new ResolutionListAdapter(layoutInflater, factory, ResolutionData.getData()));
        resolutionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                TextView value = (TextView) findViewById(R.id.density_result_density_value_text);
                TextView category = (TextView) findViewById(R.id.density_result_density_value_category);
                final ResolutionElement item = (ResolutionElement) resolutionList.getAdapter().getItem(position);
                final DensityCalculator.DensityCaluclation caluclation =
                        DensityApplication.getCalcualtor().getDensityFor(item.width, item.height, getScreenDiagonal());
                if (caluclation.isValid()) {
                    value.setText(String.valueOf(caluclation.getResult()));
                    category.setText(DensityApplication.getSifter().sift(caluclation.getResult()).toString());
                } else {
                    Toast.makeText(DensityAppActivity.this, "Invalid values chosen for screen size and/or resolution.", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private double getScreenDiagonal() {
        final SeekBar integerBar = (SeekBar) findViewById(R.id.app_screen_screensize_integer_progress);
        final SeekBar decimalBar = (SeekBar) findViewById(R.id.app_screen_screensize_decimal_progress);
        final CharSequence screenDiagonal = DensityApplication.getResolver().resolve(integerBar.getProgress(), decimalBar.getProgress());
        try {
            return Double.parseDouble(screenDiagonal.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void setInitialProgress(final SeekBar integerBar, final SeekBar decimalBar) {
        integerBar.setProgress(0); //2
        decimalBar.setProgress(5); //.5
    }

    private static class DefaultSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) { }

        @Override public void onStartTrackingTouch(final SeekBar seekBar) { }

        @Override public void onStopTrackingTouch(final SeekBar seekBar) { }
    }
}

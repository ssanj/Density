/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

import com.robodreamz.density.R;
import com.robodreamz.density.calc.DensitySifter;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.delegate.TextViewDelegate;

public class DefaultDensity {

    private ActivityDelegate activity;

    public DefaultDensity(final ActivityDelegate activity) {
        this.activity = activity;
    }

    public void setValue() {
        final TextViewDelegate value = (TextViewDelegate) activity.findViewById(R.id.density_result_density_value_text);
        final TextViewDelegate category = (TextViewDelegate) activity.findViewById(R.id.density_result_density_value_category);
        final ListViewDelegate list = (ListViewDelegate) activity.findViewById(R.id.app_screen_resolution_list);

        value.setText("0");
        category.setText(DensitySifter.DPI.NODPI.toString());
    }
}

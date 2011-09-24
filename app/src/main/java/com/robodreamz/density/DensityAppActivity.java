/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.fragment.DensityResultFragmentSetup;
import com.robodreamz.density.fragment.ResolutionListFragmentSetup;
import com.robodreamz.density.fragment.ScreenSizeFragmentSetup;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DefaultDensity;
import com.robodreamz.density.screen.DensityResultCalculator;

public final class DensityAppActivity extends AbstractDensityActivty {

    private ResolutionListFragmentSetup resolutionListFragmentSetup;

    @Override protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_screen);

        final DelegateFactory factory = DensityApplication.getFactory();
        final ActivityDelegate activityDelegate = createActivityDelegate(factory);
        final DefaultDensity defaultDensity = createDefaultDensity(activityDelegate);
        final DensityResultCalculator densityResultCalculator = createDensityResultCalcualtor(activityDelegate);

        //We need to maintain the order of these "fragments" mainly because, multiple fragments can use the resolution list and update the
        //final density. So we must ensure that the resolution list and the density widgets are created before they are used.
        resolutionListFragmentSetup = new ResolutionListFragmentSetup(activityDelegate, densityResultCalculator);
        resolutionListFragmentSetup.setup();
        new DensityResultFragmentSetup(defaultDensity).setup();
        new ScreenSizeFragmentSetup(activityDelegate, factory).setup(densityResultCalculator);
    }

    @Override protected void onResume() {
        super.onResume();
        resolutionListFragmentSetup.onResume(DensityApplication.getConstants());
    }

    private ActivityDelegate createActivityDelegate(final DelegateFactory factory) {
        return factory.createActivityDelegate(this);
    }

    private DefaultDensity createDefaultDensity(final ActivityDelegate activityDelegate) {
        return new DefaultDensity(activityDelegate);
    }

    private DensityResultCalculator createDensityResultCalcualtor(final ActivityDelegate activityDelegate) {
        return new DensityResultCalculator(
                activityDelegate,
                DensityApplication.getCalcualtor(),
                DensityApplication.getResolver(),
                DensityApplication.getSifter(),
                DensityApplication.getConstants());
    }

    @Override protected Dialog onCreateDialog(final int id) {
        return createDeleteDialog(id);
    }

    private Dialog createDeleteDialog(final int id) {
        if (id >= 1000) {
            final int position = id - 1000;
            final ListView resolutionList = (ListView) findViewById(R.id.app_screen_resolution_list);
            final ResolutionListAdapter adapter = (ResolutionListAdapter) resolutionList.getAdapter();
            final ResolutionElement element = (ResolutionElement) adapter.getItem(position);
            return new AlertDialog.Builder(DensityAppActivity.this).
                    setTitle("Delete Resolution").
                    setMessage("Delete " + element.getWidth() + "x" + element.getHeight() + " ?").
                    setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override public void onClick(final DialogInterface dialog, final int which) {
                            adapter.removeItem(position);
                            final int itemCount = adapter.getCount();
                            int newPosition = DensityApplication.getConstants().getInvalidPositionIndex();
                            if (itemCount > 0) {
                                if (position  >= itemCount) {
                                    newPosition = (position - 1) > itemCount ? 0 : position - 1;
                                } else {
                                    newPosition = position;
                                }
                                adapter.resetState();
                                resolutionList.performItemClick(null, newPosition, newPosition);
                            } else  {
                                ResolutionData.INDEX_PAIR.update(newPosition);
                            }
                            dialog.dismiss();
                        }
                    }).
                    setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override public void onClick(final DialogInterface dialog, final int which) {
                            adapter.unmarkForDeletion(position);
                            dialog.dismiss();
                        }
                    }).create();
        } else {
            return super.onCreateDialog(id);
        }
    }
}

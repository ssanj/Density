/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.delegate.ListViewDelegate;
import com.robodreamz.density.fragment.DensityResultFragmentSetup;
import com.robodreamz.density.fragment.OnDeletionSelectedPositionFinder;
import com.robodreamz.density.fragment.OnNoDeleteCustomResolutionDialogListener;
import com.robodreamz.density.fragment.OnYesDeleteCustomResolutionDialogListener;
import com.robodreamz.density.fragment.ResolutionListFragmentSetup;
import com.robodreamz.density.fragment.ScreenSizeFragmentSetup;
import com.robodreamz.density.resolution.DeletionItemIndex;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DefaultDensity;
import com.robodreamz.density.screen.DensityResultCalculator;

public final class DensityAppActivity extends AbstractDensityActivty {

    private static final int DELETE_RESOLUTION_DIALOG = DensityApplication.DENSITY_APP_DELETE_RESOLUTION_DIALOG;
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

    //TODO: Test
    @Override protected void onPrepareDialog(final int id, final Dialog dialog) {
        if (id == DELETE_RESOLUTION_DIALOG) {
            final TextView message = (TextView) dialog.findViewById(android.R.id.message);
            if (message != null) {
                final DeletionItemIndex deletionIndex = ResolutionData.DELETION_INDEX;
                if (deletionIndex.isValid()) {
                    final ListView resolutionList = (ListView) findViewById(R.id.app_screen_resolution_list);
                    final ResolutionListAdapter adapter = (ResolutionListAdapter) resolutionList.getAdapter();
                    message.setText(DensityApplication.getDeleteCustomResolutionDialogBuilder().
                            createResolutionText((ResolutionElement) adapter.getItem(deletionIndex.getValue())));
                }
            }
        } else {
            super.onPrepareDialog(id, dialog);
        }
    }

    //TODO: Test
    private Dialog createDeleteDialog(final int id) {
        if (id == DELETE_RESOLUTION_DIALOG) {
            final ListView resolutionList = (ListView) findViewById(R.id.app_screen_resolution_list);
            final ResolutionListAdapter adapter = (ResolutionListAdapter) resolutionList.getAdapter();
            final DeletionItemIndex deletionIndex = ResolutionData.DELETION_INDEX;
            if (deletionIndex.isValid()) {
                final ResolutionElement element = (ResolutionElement) adapter.getItem(deletionIndex.getValue());
                final OnDeletionSelectedPositionFinder finder = DensityApplication.getOnDeleteSelectionFinder();
                final ListViewDelegate resolutionListDelegate = (ListViewDelegate) DensityApplication.getFactory().createViewDelegate(resolutionList);
                return DensityApplication.getDeleteCustomResolutionDialogBuilder().create(
                        this,
                        element,
                        new OnYesDeleteCustomResolutionDialogListener(resolutionListDelegate, adapter, finder),
                        new OnNoDeleteCustomResolutionDialogListener(adapter));
            } else {
               return super.onCreateDialog(id);
            }
        } else {
            return super.onCreateDialog(id);
        }
    }
}

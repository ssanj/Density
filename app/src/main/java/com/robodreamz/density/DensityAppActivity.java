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
import android.widget.TextView;
import com.robodreamz.density.delegate.ActivityDelegate;
import com.robodreamz.density.delegate.Constants;
import com.robodreamz.density.delegate.DelegateFactory;
import com.robodreamz.density.fragment.CancelDeleteCustomResolutionDialogListener;
import com.robodreamz.density.fragment.DeleteCustomResolutionDialogBuilder;
import com.robodreamz.density.fragment.DensityResultFragmentSetup;
import com.robodreamz.density.fragment.OnDeletionSelectedPositionFinder;
import com.robodreamz.density.fragment.ResolutionListFragmentSetup;
import com.robodreamz.density.fragment.ScreenSizeFragmentSetup;
import com.robodreamz.density.resolution.ResolutionData;
import com.robodreamz.density.resolution.ResolutionElement;
import com.robodreamz.density.resolution.ResolutionListAdapter;
import com.robodreamz.density.screen.DefaultDensity;
import com.robodreamz.density.screen.DensityResultCalculator;
import org.w3c.dom.Text;

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

    @Override protected void onPrepareDialog(final int id, final Dialog dialog) {
        if (id == DELETE_RESOLUTION_DIALOG) {
            final TextView message = (TextView) dialog.findViewById(android.R.id.message);
            if (message != null) {
                final int position = ResolutionData.DELETION_INDEX.get();
                if (!DensityApplication.getConstants().isInvalidPosition(position)) {
                    final ListView resolutionList = (ListView) findViewById(R.id.app_screen_resolution_list);
                    final ResolutionListAdapter adapter = (ResolutionListAdapter) resolutionList.getAdapter();
                    message.setText(DensityApplication.getDeleteCustomResolutionDialogBuilder().
                            createResolutionText((ResolutionElement) adapter.getItem(position)));
                }
            }
        } else {
            super.onPrepareDialog(id, dialog);
        }
    }

    private Dialog createDeleteDialog(final int id) {
        if (id == DELETE_RESOLUTION_DIALOG) {
            final ListView resolutionList = (ListView) findViewById(R.id.app_screen_resolution_list);
            final ResolutionListAdapter adapter = (ResolutionListAdapter) resolutionList.getAdapter();
            final Constants constants = DensityApplication.getConstants();
            int deletionIndex = ResolutionData.DELETION_INDEX.get();
            if (!constants.isInvalidPosition(deletionIndex)) {
                final ResolutionElement element = (ResolutionElement) adapter.getItem(deletionIndex);
                final AlertDialog dialog = DensityApplication.getDeleteCustomResolutionDialogBuilder().
                        create(this, element, new DialogInterface.OnClickListener() {
                            @Override public void onClick(final DialogInterface dialog, final int which) {
                                int position = ResolutionData.DELETION_INDEX.get();
                                if (!constants.isInvalidPosition(position)) {
                                    adapter.removeItem(position);
                                    final int listSizeAfterDeletion = adapter.getCount();
                                    final OnDeletionSelectedPositionFinder finder = DensityApplication.getOnDeleteSelectionFinder();
                                    final int newPosition = finder.findSelectionPosition(listSizeAfterDeletion, position);
                                    adapter.resetState();

                                    if (!constants.isInvalidPosition(newPosition)) {
                                        resolutionList.performItemClick(null, newPosition, newPosition);
                                    } else {
                                        ResolutionData.INDEX_PAIR.update(constants.getInvalidPositionIndex()); //the list should be empty at this point.
                                    }
                                }

                                ResolutionData.DELETION_INDEX.set(constants.getInvalidPositionIndex());
                                dialog.dismiss();
                            }
                        }, new CancelDeleteCustomResolutionDialogListener(adapter));

                return dialog;
            } else {
               return super.onCreateDialog(id);
            }
        } else {
            return super.onCreateDialog(id);
        }
    }
}

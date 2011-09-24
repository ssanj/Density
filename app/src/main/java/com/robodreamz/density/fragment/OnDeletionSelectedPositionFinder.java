/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

public final class OnDeletionSelectedPositionFinder {

    private final int invalidIndex;

    public OnDeletionSelectedPositionFinder(final int invalidIndex) {
        this.invalidIndex = invalidIndex;
    }

    public int findSelectionPosition(final int listSizeAfterDelete, final int deletedPosition) {
        if (deletedPosition < listSizeAfterDelete) {
            return deletedPosition;
        } else {
            if (listSizeAfterDelete > 0) {
                int possiblePos = deletedPosition - 1;
                return (possiblePos < listSizeAfterDelete) ? possiblePos : 0;
            } else {
                return invalidIndex;
            }
        }
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.fragment;

public class OnDeletionSelectedPositionFinder {

    private final int invalidIndex;

    public OnDeletionSelectedPositionFinder(final int invalidIndex) {
        this.invalidIndex = invalidIndex;
    }

    //for testing
    OnDeletionSelectedPositionFinder() {
        invalidIndex = -1;
    }

    public OnDeletionSelectedPosition findSelectionPosition(final int listSizeAfterDelete, final int deletedPosition) {
        if (deletedPosition < listSizeAfterDelete) {
            return new OnDeletionSelectedPosition(deletedPosition, invalidIndex);
        } else {
            if (listSizeAfterDelete > 0) {
                int possiblePos = deletedPosition - 1;
                return new OnDeletionSelectedPosition((possiblePos < listSizeAfterDelete) ? possiblePos : 0, invalidIndex);
            } else {
                return new OnDeletionSelectedPosition(invalidIndex);
            }
        }
    }

    public static final class OnDeletionSelectedPosition {

        private final int position;
        private final int invalidIndex;

        public OnDeletionSelectedPosition(final int position, final int invalidIndex) {
            this.position = position;
            this.invalidIndex = invalidIndex;
        }

        public OnDeletionSelectedPosition(final int invalidIndex) {
            this.position = invalidIndex;
            this.invalidIndex = invalidIndex;
        }

        public boolean isValid() {
            return position != invalidIndex;
        }

        public boolean isInvalid() {
            return !isValid();
        }

        public int getPosition() {
            return position;
        }
    }
}

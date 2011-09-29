/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import java.util.Comparator;

/**
 * <code>Comparator<ResolutionItem>/code> that compares <code>ResolutionElement</code>s based on their width.
 * If an item that is not a <code>ResolutionElement</code> is found it will always be sorted before a
 * <code>ResolutionElement</code>.
 */
public final class WidthBasedResolutionItemComparator implements Comparator<ResolutionItem> {

    private static final int AFTER = 1;
    private static final int BEFORE = -1;

    @Override public int compare(final ResolutionItem resolutionItem1, final ResolutionItem resolutionItem2) {
        if (resolutionItem1 instanceof ResolutionElement && resolutionItem2 instanceof ResolutionElement) {
            return (int) Math.signum(((ResolutionElement) resolutionItem1).getWidth() - ((ResolutionElement) resolutionItem2).getWidth());
        } else { //one of the two items is not of ResolutionElement
            return (resolutionItem1 instanceof  ResolutionElement) ? AFTER : BEFORE;
        }
    }

    @Override public boolean equals(final Object o) {
        return (o != null && o instanceof WidthBasedResolutionItemComparator);
    }
}

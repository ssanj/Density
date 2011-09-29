/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class WidthBasedResolutionItemComparatorWithLiveDataTest {

    private WidthBasedResolutionItemComparator comparator;

    @Before public void setup() {
        comparator = new WidthBasedResolutionItemComparator();
    }

    @Test public void shouldSortAsExpected() {
        final Set<ResolutionItem> items = new TreeSet<ResolutionItem>(comparator);
        items.add(resolutionElement(960, 540));
        items.add(resolutionElement(480, 320));
        items.add(resolutionElement(320, 240));
        items.add(resolutionHeader());
        items.add(resolutionElement(540, 960));
        items.add(customResolutionElement(873, 510));
        items.add(resolutionElement(400, 240));
        items.add(resolutionElement(800, 480));
        items.add(resolutionElement(854, 480));
        items.add(resolutionElement(1024, 600));
        items.add(resolutionElement(1200, 800));
        items.add(customResolutionElement(410, 360));

        final Iterator<ResolutionItem> it = items.iterator();
        expectResolutionHeader(it.next());
        expectResolutionElement(it.next(), 320, 240);
        expectResolutionElement(it.next(), 400, 240);
        expectResolutionElement(it.next(), 410, 360);
        expectResolutionElement(it.next(), 480, 320);
        expectResolutionElement(it.next(), 540, 960);
        expectResolutionElement(it.next(), 800, 480);
        expectResolutionElement(it.next(), 854, 480);
        expectResolutionElement(it.next(), 873, 510);
        expectResolutionElement(it.next(), 960, 540);
        expectResolutionElement(it.next(), 1024, 600);
        expectResolutionElement(it.next(), 1200, 800);
    }

    private void expectResolutionElement(final  ResolutionItem item, final int width, final int height) {
        assertEquals("Incorrect width", width, ((ResolutionElement) item).getWidth());
        assertEquals("Incorrect height", height, ((ResolutionElement) item).getHeight());
    }

    private void expectResolutionHeader(final ResolutionItem next) {
        assertTrue("Expected ResolutionHeader", ResolutionHeader.class.isAssignableFrom(next.getClass()));
    }

    private ResolutionItem resolutionHeader() {
        return new ResolutionHeader();
    }

    private StandardResolutionElement resolutionElement(int width, int height) {
        return new StandardResolutionElement(width, height);
    }

    private CustomResolutionElement customResolutionElement(int width, int height) {
        return new CustomResolutionElement(resolutionElement(width, height));
    }
}

/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.screen;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public final class ScreenSizeResolverTest {

    private ScreenSizeResolver resolver;

    @Before public void setup() {
        resolver = new ScreenSizeResolver();
    }

    @Test public void shouldconvertProgressValueToActualString() throws Exception {
        assertThat("Incorrect screen resolution", resolver.convertProgressValueToActualString(100, 2).toString(), equalTo("102.2"));
    }

    @Test public void shoulConvertProgressValueToActualStringWhenProgressValueIsZero() throws Exception {
        assertThat("Incorrect screen resolution", resolver.convertProgressValueToActualString(0, 7).toString(), equalTo("2.7"));
    }

    @Test public void shouldConvertProgressValueToActualInt() {
        for (int count = 0; count < 10; count++) {
            assertThat("Incorrect integral actual int for: " + count, resolver.convertProgressValueToActualInt(count),
                    equalTo(count + ScreenSizeResolver.INTEGRAL_SCREENSIZE_OFFSET));
        }
    }

    @Test public void shouldConvertActualValueToProgressInt() {
        for (int count = ScreenSizeResolver.INTEGRAL_SCREENSIZE_OFFSET; count < 10 + ScreenSizeResolver.INTEGRAL_SCREENSIZE_OFFSET; count++) {
            assertThat("Incorrect integral progress for: " + count, resolver.convertActualValueToProgressInt(count),
                    equalTo(count - ScreenSizeResolver.INTEGRAL_SCREENSIZE_OFFSET));
        }

    }
}

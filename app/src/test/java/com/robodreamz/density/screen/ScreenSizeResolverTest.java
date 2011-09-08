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

    @Test public void shouldResolveAGivenIntegralAndDecimalValue() throws Exception {
        assertThat("Incorrect screen resolution", resolver.resolve(100, 2).toString(), equalTo("102.2"));
    }

    @Test public void shouldOffsetIntegralValue() throws Exception {
        assertThat("Incorrect screen resolution", resolver.resolve(0, 7).toString(), equalTo("2.7"));
    }
}

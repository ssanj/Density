/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.resolution;

public final class ResolutionReserver {

    public ResolutionElement reverse(final ResolutionElement element) {
        if (element instanceof CustomResolutionElement) {
            return new CustomResolutionElement(new StandardResolutionElement(element.getHeight(), element.getWidth()));
        } else {
            return new StandardResolutionElement(element.getHeight(), element.getWidth());
        }
    }
}

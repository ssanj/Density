/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package com.robodreamz.density.delegate;

import android.widget.TextView;

public class TextViewDelegate extends ViewDelegate {

    public TextViewDelegate(final TextView view, final DelegateFactory delegateFactory) {
        super(view, delegateFactory);
    }

    public CharSequence getText() {
        if (isNull()) {
            return null;
        } else {
            return ((TextView) view).getText();
        }
    }

    public void setText(CharSequence text) {
        if (!isNull()) {
            ((TextView) view).setText(text);
        }
    }
}

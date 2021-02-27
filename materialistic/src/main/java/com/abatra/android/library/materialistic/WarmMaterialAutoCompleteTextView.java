package com.abatra.android.library.materialistic;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class WarmMaterialAutoCompleteTextView extends MaterialAutoCompleteTextView {

    private static final boolean FREEZES_TEXT = false;

    public WarmMaterialAutoCompleteTextView(Context context) {
        super(context);
    }

    public WarmMaterialAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WarmMaterialAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean getFreezesText() {
        return FREEZES_TEXT;
    }
}

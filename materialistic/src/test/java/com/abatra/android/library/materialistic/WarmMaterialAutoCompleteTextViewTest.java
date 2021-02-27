package com.abatra.android.library.materialistic;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class WarmMaterialAutoCompleteTextViewTest {

    private WarmMaterialAutoCompleteTextView autoCompleteTextView;

    @Before
    public void setup() {
        Context applicationContext = ApplicationProvider.getApplicationContext();
        applicationContext.setTheme(R.style.Theme_AppCompat);
        autoCompleteTextView = new WarmMaterialAutoCompleteTextView(applicationContext);
    }

    @Test
    public void testGetFreezesTextReturnsFalse() {
        assertFalse(autoCompleteTextView.getFreezesText());
    }
}

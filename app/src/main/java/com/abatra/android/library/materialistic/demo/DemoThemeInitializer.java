package com.abatra.android.library.materialistic.demo;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.startup.Initializer;

import com.abatra.android.library.materialistic.theme.ThemeInitializer;

import java.util.Collections;
import java.util.List;

import static com.abatra.android.library.materialistic.demo.SettingsFragment.KEY_THEME_NIGHT_MODE_DROP_DOWN;
import static com.abatra.android.library.materialistic.theme.ThemePreferencePresenter.DEFAULT_VALUE;

public class DemoThemeInitializer extends ThemeInitializer {

    @Override
    protected int getNightMode(@NonNull Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String nightModeString = sharedPreferences.getString(KEY_THEME_NIGHT_MODE_DROP_DOWN, DEFAULT_VALUE);
        return Integer.parseInt(nightModeString);
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}

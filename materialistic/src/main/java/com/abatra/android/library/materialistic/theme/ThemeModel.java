package com.abatra.android.library.materialistic.theme;

import android.os.Build;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDelegate;

import com.abatra.android.library.materialistic.R;

public class ThemeModel {

    public static final ThemeModel DEFAULT = new ThemeModel(R.string.device, getRecommendedDefaultNightMode());
    public static final ThemeModel NIGHT_MODE_YES = new ThemeModel(R.string.dark, AppCompatDelegate.MODE_NIGHT_YES);
    public static final ThemeModel NIGHT_MODE_NO = new ThemeModel(R.string.light, AppCompatDelegate.MODE_NIGHT_NO);

    @StringRes
    private final int name;
    private final int nightMode;

    public ThemeModel(@StringRes int name, int nightMode) {
        this.name = name;
        this.nightMode = nightMode;
    }

    private static int getRecommendedDefaultNightMode() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                ? AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                : AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY;
    }

    @StringRes
    public int getName() {
        return name;
    }

    public int getNightMode() {
        return nightMode;
    }
}

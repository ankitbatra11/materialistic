package com.abatra.android.library.materialistic.demo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.abatra.android.library.materialistic.theme.ThemeModel;
import com.abatra.android.library.materialistic.theme.ThemePreferencePresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static final String KEY_THEME_NIGHT_MODE_DROP_DOWN = "themeNightModeDropDown";
    public static final String KEY_THEME_NIGHT_MODE_LIST = "themeNightModeList";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeThemePreference(findPreference(KEY_THEME_NIGHT_MODE_DROP_DOWN));
        initializeThemePreference(findPreference(KEY_THEME_NIGHT_MODE_LIST));
    }

    private void initializeThemePreference(ListPreference listPreference) {
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            Snackbar.make(requireView(), "theme changed to " + newValue, Snackbar.LENGTH_LONG).show();
            return true;
        });
        ThemePreferencePresenter presenter = new ThemePreferencePresenter(listPreference);
        presenter.initializePreference(Arrays.asList(ThemeModel.DEFAULT, ThemeModel.NIGHT_MODE_NO, ThemeModel.NIGHT_MODE_YES));
    }
}

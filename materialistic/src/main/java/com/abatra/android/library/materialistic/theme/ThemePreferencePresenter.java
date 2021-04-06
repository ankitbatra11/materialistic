package com.abatra.android.library.materialistic.theme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ThemePreferencePresenter {

    public static final String DEFAULT_VALUE = mapEntryValue(ThemeModel.DEFAULT);

    private final ListPreference listPreference;

    public ThemePreferencePresenter(ListPreference listPreference) {
        this.listPreference = listPreference;
    }

    public void initializePreference(List<ThemeModel> themeModels) {

        List<ThemeModel> notNullModels = Optional.ofNullable(themeModels).orElse(Collections.emptyList());
        listPreference.setEntries(mapEntries(notNullModels));
        listPreference.setEntryValues(mapEntryValues(notNullModels));
        listPreference.setDefaultValue(DEFAULT_VALUE);
        updateSummary(notNullModels, getCurrentOrDefaultEntryValue());

        Preference.OnPreferenceChangeListener delegate = listPreference.getOnPreferenceChangeListener();
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean result = isChangeHandled(delegate, preference, newValue);
            if (result) {
                int nightMode = Integer.parseInt(newValue.toString());
                AppCompatDelegate.setDefaultNightMode(nightMode);
                updateSummary(notNullModels, nightMode);
            }
            return result;
        });
    }

    private void updateSummary(List<ThemeModel> notNullModels, int nightMode) {
        getEntryValue(notNullModels, nightMode).ifPresent(listPreference::setSummary);
    }

    private int getCurrentOrDefaultEntryValue() {
        String nightModeString = Optional.ofNullable(listPreference.getValue()).orElse(DEFAULT_VALUE);
        return Integer.parseInt(nightModeString);
    }

    private Optional<String> getEntryValue(List<ThemeModel> themeModels, int nightMode) {
        return themeModels.stream()
                .filter(model -> model.getNightMode() == nightMode)
                .findFirst()
                .map(model -> listPreference.getContext().getString(model.getName()));
    }

    private static String mapEntryValue(ThemeModel themeModel) {
        return String.valueOf(themeModel.getNightMode());
    }

    private Boolean isChangeHandled(@Nullable Preference.OnPreferenceChangeListener delegate,
                                    Preference preference,
                                    Object newValue) {
        return Optional.ofNullable(delegate)
                .map(l -> l.onPreferenceChange(preference, newValue))
                .orElse(true);
    }

    private CharSequence[] mapEntryValues(List<ThemeModel> themeModels) {
        return themeModels.stream()
                .map(ThemePreferencePresenter::mapEntryValue)
                .toArray(CharSequence[]::new);
    }

    private CharSequence[] mapEntries(List<ThemeModel> themeModels) {
        return themeModels.stream()
                .map(model -> listPreference.getContext().getString(model.getName()))
                .toArray(CharSequence[]::new);
    }
}

package com.abatra.android.library.materialistic.theme;

import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.abatra.android.library.materialistic.R;

import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.abatra.android.library.materialistic.theme.ThemeModel.DEFAULT;
import static com.abatra.android.library.materialistic.theme.ThemeModel.NIGHT_MODE_NO;
import static com.abatra.android.library.materialistic.theme.ThemeModel.NIGHT_MODE_YES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class ThemePreferencePresenterTest {

    @Mock
    private ListPreference mockedListPreference;

    @InjectMocks
    private ThemePreferencePresenter presenter;

    @Captor
    private ArgumentCaptor<CharSequence[]> entriesArgumentCaptor;

    @Captor
    private ArgumentCaptor<CharSequence[]> entryValuesArgumentCaptor;

    @Mock
    private Preference.OnPreferenceChangeListener mockedOnPreferenceChangeListener;

    @Mock
    private Preference mockedPreference;

    @Captor
    private ArgumentCaptor<Preference.OnPreferenceChangeListener> preferenceChangeListenerArgumentCaptor;

    @Before
    public void setup() {

        MockitoAnnotations.openMocks(this);

        when(mockedListPreference.getOnPreferenceChangeListener()).thenReturn(mockedOnPreferenceChangeListener);
        when(mockedListPreference.getContext()).thenReturn(getApplicationContext());
    }

    @Test
    public void testInitialize_withOnPreferenceChangeListener() {

        presenter.initializePreference(Arrays.asList(DEFAULT, NIGHT_MODE_YES, NIGHT_MODE_NO));

        verify(mockedListPreference, times(1)).setTitle(R.string.theme);

        verify(mockedListPreference, times(1)).setEntries(entriesArgumentCaptor.capture());
        assertThat(entriesArgumentCaptor.getAllValues(), hasSize(1));
        assertThat(entriesArgumentCaptor.getValue().length, equalTo(3));
        assertThat(entriesArgumentCaptor.getValue(), equalTo(new CharSequence[]{
                getNameString(DEFAULT),
                getNameString(NIGHT_MODE_YES),
                getNameString(NIGHT_MODE_NO),
        }));

        String defaultNightModeString = getNightModeString(DEFAULT);
        verify(mockedListPreference, times(1)).setEntryValues(entryValuesArgumentCaptor.capture());
        assertThat(entryValuesArgumentCaptor.getAllValues(), hasSize(1));
        assertThat(entryValuesArgumentCaptor.getValue().length, equalTo(3));
        String yesNightModeString = getNightModeString(NIGHT_MODE_YES);
        assertThat(entryValuesArgumentCaptor.getValue(), equalTo(new CharSequence[]{
                defaultNightModeString,
                yesNightModeString,
                getNightModeString(NIGHT_MODE_NO)
        }));

        verify(mockedListPreference, times(1)).setSummary(getNameString(DEFAULT));

        verify(mockedListPreference, times(1)).setOnPreferenceChangeListener(preferenceChangeListenerArgumentCaptor.capture());
        assertThat(preferenceChangeListenerArgumentCaptor.getAllValues(), hasSize(1));

        try (MockedStatic<AppCompatDelegate> appCompatDelegateMockedStatic = mockStatic(AppCompatDelegate.class)) {

            testOnPreferenceChangeWithReturnValueFalse(yesNightModeString, appCompatDelegateMockedStatic);

            testOnPreferenceChangeWithReturnValueTrue(yesNightModeString, appCompatDelegateMockedStatic, times(2));
        }
    }

    private void testOnPreferenceChangeWithReturnValueTrue(String yesNightModeString,
                                                           MockedStatic<AppCompatDelegate> appCompatDelegateMockedStatic,
                                                           VerificationMode onPreferenceChangeVerificationMode) {

        when(mockedOnPreferenceChangeListener.onPreferenceChange(any(), any())).thenReturn(true);

        preferenceChangeListenerArgumentCaptor.getValue().onPreferenceChange(mockedPreference, yesNightModeString);

        appCompatDelegateMockedStatic.verify(() -> AppCompatDelegate.setDefaultNightMode(NIGHT_MODE_YES.getNightMode()), times(1));
        verify(mockedListPreference, times(1)).setSummary(getNameString(NIGHT_MODE_YES));
        verify(mockedOnPreferenceChangeListener, onPreferenceChangeVerificationMode).onPreferenceChange(mockedPreference, yesNightModeString);
    }

    private void testOnPreferenceChangeWithReturnValueFalse(String nightModeString,
                                                            MockedStatic<AppCompatDelegate> appCompatDelegateMockedStatic) {

        when(mockedOnPreferenceChangeListener.onPreferenceChange(any(), any())).thenReturn(false);

        preferenceChangeListenerArgumentCaptor.getValue().onPreferenceChange(mockedPreference, nightModeString);

        appCompatDelegateMockedStatic.verify(() -> AppCompatDelegate.setDefaultNightMode(anyInt()), never());
        verify(mockedListPreference, never()).setSummary(nightModeString);
    }

    @NotNull
    private String getNightModeString(ThemeModel aDefault) {
        return String.valueOf(aDefault.getNightMode());
    }

    private String getNameString(ThemeModel aDefault) {
        return getApplicationContext().getString(aDefault.getName());
    }

    @Test
    public void testInitialize_withoutOnPreferenceChangeListener() {

        when(mockedListPreference.getOnPreferenceChangeListener()).thenReturn(null);

        presenter.initializePreference(Arrays.asList(DEFAULT, NIGHT_MODE_YES, NIGHT_MODE_NO));

        verify(mockedListPreference, times(1)).setOnPreferenceChangeListener(preferenceChangeListenerArgumentCaptor.capture());
        preferenceChangeListenerArgumentCaptor.getValue().onPreferenceChange(mockedPreference, getNightModeString(NIGHT_MODE_NO));
        verify(mockedOnPreferenceChangeListener, never()).onPreferenceChange(any(), any());
    }
}

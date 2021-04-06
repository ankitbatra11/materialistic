package com.abatra.android.library.materialistic;

import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class MaterialDropDownTest {

    @Mock
    private MaterialAutoCompleteTextView mockedAutoCompleteTextView;

    @Mock
    private TextInputLayout mockedTextInputLayout;

    private MaterialDropDown materialDropDown;

    @Mock
    private ArrayAdapter<String> mockedArrayAdapter;

    @Mock
    private AdapterView.OnItemClickListener mockedOnItemClickListener;

    private MaterialDropDown.SelectionTracker selectionTracker;

    @Before
    public void setup() {

        MockitoAnnotations.openMocks(this);

        when(mockedTextInputLayout.findViewById(R.id.material_drop_down_internal)).thenReturn(mockedAutoCompleteTextView);
        when(mockedTextInputLayout.getContext()).thenReturn(ApplicationProvider.getApplicationContext());

        when(mockedAutoCompleteTextView.getAdapter()).thenReturn(mockedArrayAdapter);
        when(mockedArrayAdapter.getItem(anyInt())).thenReturn("one");

        materialDropDown = MaterialDropDown.fromView(mockedTextInputLayout);
        assertThat(materialDropDown, notNullValue());
        verify(mockedTextInputLayout, times(1)).findViewById(R.id.material_drop_down_internal);
        verify(mockedAutoCompleteTextView, times(1)).setOnItemSelectedListener(any(AdapterView.OnItemSelectedListener.class));

        selectionTracker = new MaterialDropDown.SelectionTracker();
    }

    @Test
    public void test_setItems_callsSetItemsSetSelectionSetOnItemClickListener() {

        materialDropDown.setItems(Arrays.asList("material", "dropDown"), 0, mockedOnItemClickListener);

        verifySetItems();
        verifySetSelection();
        verifySetOnItemClickListener();
    }

    private void verifySetItems() {
        verify(mockedAutoCompleteTextView, times(1)).setAdapter(any(ArrayAdapter.class));
    }

    private void verifySetSelection() {
        assertThat(materialDropDown.getSelection().isPresent(), equalTo(true));
        verify(mockedAutoCompleteTextView, times(1)).setSelection(0);
        verify(mockedAutoCompleteTextView, times(1)).setText("one", false);
    }

    private void verifySetOnItemClickListener() {
        verify(mockedAutoCompleteTextView, times(1)).setOnItemClickListener(mockedOnItemClickListener);
    }

    @Test
    public void test_setItems_setsAdapter() {

        materialDropDown.setItems(Arrays.asList("material", "dropDown"));

        verifySetItems();
    }

    @Test
    public void test_setSelection_jellyBeanOrAbove() {

        assertThat(materialDropDown.getSelection().isPresent(), equalTo(false));

        materialDropDown.setSelection(0);

        verifySetSelection();
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.JELLY_BEAN)
    public void test_setSelection_belowJellyBean_doesNotCallSetText() {

        assertThat(materialDropDown.getSelection().isPresent(), equalTo(false));

        materialDropDown.setSelection(0);

        assertThat(materialDropDown.getSelection().isPresent(), equalTo(true));
        verify(mockedAutoCompleteTextView, times(1)).setSelection(0);
        verify(mockedAutoCompleteTextView, never()).setText(anyString(), anyBoolean());
    }


    @Test
    public void test_setOnItemClickListener() {

        materialDropDown.setOnItemClickListener(mockedOnItemClickListener);

        ArgumentCaptor<AdapterView.OnItemClickListener> captor = ArgumentCaptor.forClass(AdapterView.OnItemClickListener.class);
        verify(mockedAutoCompleteTextView, times(1)).setOnItemClickListener(captor.capture());
        captor.getValue().onItemClick(null, null, 1, 0);
        verify(mockedOnItemClickListener, times(1)).onItemClick(null, null, 1, 0);

        assertThat(materialDropDown.getSelection().isPresent(), equalTo(true));
        assertThat(materialDropDown.getSelection().get(), equalTo(1));
    }

    @Test
    public void test_selectionTracker_onItemSelected() {

        assertThat(selectionTracker.getSelection().isPresent(), equalTo(false));

        selectionTracker.onItemSelected(null, null, 1, 1);

        assertThat(selectionTracker.getSelection().isPresent(), equalTo(true));
        assertThat(selectionTracker.getSelection().get(), equalTo(1));
    }

    @Test
    public void test_selectionTracker_onNothingSelected() {

        //noinspection rawtypes
        AdapterView adapterView = Mockito.mock(AdapterView.class);

        selectionTracker.onNothingSelected(adapterView);

        verifyNoInteractions(adapterView);
    }
}
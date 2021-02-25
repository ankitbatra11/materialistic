package com.abatra.android.library.materialistic;

import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Optional;

public class MaterialDropDown {

    private final TextInputLayout materialDropDownParent;
    private final MaterialAutoCompleteTextView dropDown;
    private final SelectionTracker selectionTracker;

    private MaterialDropDown(TextInputLayout materialDropDownParent,
                             MaterialAutoCompleteTextView dropDown,
                             SelectionTracker selectionTracker) {
        this.materialDropDownParent = materialDropDownParent;
        this.dropDown = dropDown;
        this.selectionTracker = selectionTracker;
    }

    public static MaterialDropDown fromView(TextInputLayout materialDropDownParent) {
        MaterialAutoCompleteTextView dropDown = materialDropDownParent.findViewById(R.id.material_drop_down_internal);
        SelectionTracker selectionTracker = new SelectionTracker();
        dropDown.setOnItemSelectedListener(selectionTracker);
        return new MaterialDropDown(materialDropDownParent, dropDown, selectionTracker);
    }

    public MaterialDropDown setItems(List<String> items, int selection, AdapterView.OnItemClickListener onItemClickListener) {
        return setItems(items).setSelection(selection).setOnItemClickListener(onItemClickListener);
    }

    public MaterialDropDown setItems(List<String> items) {

        dropDown.setAdapter(new ArrayAdapter<>(materialDropDownParent.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items));

        return this;
    }

    public MaterialDropDown setSelection(int pos) {
        selectionTracker.setSelection(pos);
        dropDown.setSelection(pos);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dropDown.setText((CharSequence) dropDown.getAdapter().getItem(pos), false);
        }
        return this;
    }

    public Optional<Integer> getSelection() {
        return selectionTracker.getSelection();
    }

    public MaterialDropDown setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        dropDown.setOnItemClickListener(onItemClickListener);
        return this;
    }

    private static class SelectionTracker implements AdapterView.OnItemSelectedListener {

        @Nullable
        private Integer selection;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selection = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

        private Optional<Integer> getSelection() {
            return Optional.ofNullable(selection);
        }

        private void setSelection(int selection) {
            this.selection = selection;
        }
    }

    public interface OnItemSelectedListener extends AdapterView.OnItemSelectedListener {

        @Override
        default void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        }

        @Override
        default void onNothingSelected(AdapterView<?> parent) {
        }
    }
}

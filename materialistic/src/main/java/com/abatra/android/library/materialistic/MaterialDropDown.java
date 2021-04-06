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
        return setItems(items)
                .setSelection(selection)
                .setOnItemClickListener(onItemClickListener);
    }

    public MaterialDropDown setItems(List<String> items) {

        dropDown.setAdapter(new ArrayAdapter<>(materialDropDownParent.getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items));

        return this;
    }

    public MaterialDropDown setSelection(int pos) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dropDown.setText((CharSequence) dropDown.getAdapter().getItem(pos), false);
        }
        dropDown.setSelection(pos);
        selectionTracker.setSelection(pos);
        return this;
    }

    public Optional<Integer> getSelection() {
        return selectionTracker.getSelection();
    }

    public MaterialDropDown setOnItemClickListener(@Nullable AdapterView.OnItemClickListener onItemClickListener) {
        dropDown.setOnItemClickListener(wrapOnItemClickListener(onItemClickListener));
        return this;
    }

    private AdapterView.OnItemClickListener wrapOnItemClickListener(@Nullable AdapterView.OnItemClickListener onItemClickListener) {
        return Optional.ofNullable(onItemClickListener)
                .map(l -> (AdapterView.OnItemClickListener) (parent, view, position, id) -> {
                    setSelection(position);
                    l.onItemClick(parent, view, position, id);
                })
                .orElse(null);
    }

    static class SelectionTracker implements AdapterView.OnItemSelectedListener {

        @Nullable
        private Integer selection;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setSelection(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

        Optional<Integer> getSelection() {
            return Optional.ofNullable(selection);
        }

        void setSelection(int selection) {
            this.selection = selection;
        }
    }
}

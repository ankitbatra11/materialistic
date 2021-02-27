package com.abatra.android.library.materialistic.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.android.library.materialistic.MaterialDropDown;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.critical_permission_btn).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CriticalPermissionActivity.class);
            startActivity(intent);
        });

        List<String> items = Arrays.asList("Material", "Drop", "Down");
        MaterialDropDown materialDropDown = MaterialDropDown.fromView(findViewById(R.id.material_drop_down));
        materialDropDown.setItems(items)
                .setSelection(1)
                .setOnItemClickListener((parent, view, position, id) -> showMessage("onItemClick " + items.get(position)));

        findViewById(R.id.button_get_selection).setOnClickListener(v -> {
            Optional<Integer> selection = materialDropDown.getSelection();
            selection.ifPresent(integer -> showMessage("current selection=" + items.get(integer)));
        });
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_LONG).show();
    }
}
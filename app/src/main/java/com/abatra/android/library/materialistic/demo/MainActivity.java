package com.abatra.android.library.materialistic.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.abatra.android.library.materialistic.MaterialDropDown;
import com.abatra.android.library.materialistic.demo.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        binding.criticalPermissionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CriticalPermissionActivity.class);
            startActivity(intent);
        });

        List<String> items = Arrays.asList("Material", "Drop", "Down");
        MaterialDropDown materialDropDown = MaterialDropDown.fromView(binding.dropDownMaterial.getRoot());
        materialDropDown.setItems(items)
                .setSelection(1)
                .setOnItemClickListener((parent, view, position, id) -> showMessage("onItemClick " + items.get(position)));

        binding.buttonGetSelection.setOnClickListener(v -> {
            Optional<Integer> selection = materialDropDown.getSelection();
            selection.ifPresent(integer -> showMessage("current selection=" + items.get(integer)));
        });

        binding.buttonOpenSettings.setOnClickListener(v -> startActivity(new Intent(v.getContext(), SettingsActivity.class)));
    }

    private void showMessage(String message) {
        Snackbar.make(findViewById(R.id.root), message, Snackbar.LENGTH_LONG).show();
    }
}
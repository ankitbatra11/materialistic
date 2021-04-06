package com.abatra.android.library.materialistic.demo;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abatra.android.library.materialistic.demo.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySettingsBinding binding = ActivitySettingsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, new SettingsFragment())
                .commit();
    }
}

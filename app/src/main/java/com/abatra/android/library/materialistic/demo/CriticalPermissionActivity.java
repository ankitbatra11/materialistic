package com.abatra.android.library.materialistic.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

public class CriticalPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critical_permission);

        TextView textView = findViewById(R.id.screen_critical_perm_btn_exit);
        textView.setText(R.string.exit);
        textView.setOnClickListener(v -> finish());

        textView = findViewById(R.id.screen_critical_perm_btn_grant_perm);
        textView.setText(R.string.settings);

        AppCompatImageView appCompatImageView = findViewById(R.id.screen_critical_perm_image);
        appCompatImageView.setImageResource(R.drawable.ic_baseline_perm_media_24);

        textView = findViewById(R.id.screen_critical_perm_rationale);
        textView.setText(R.string.critical_permission_rationale);

    }
}

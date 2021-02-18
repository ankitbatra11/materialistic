package com.abatra.android.library.materialistic.demo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.critical_permission_btn).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CriticalPermissionActivity.class);
            startActivity(intent);
        });
    }
}
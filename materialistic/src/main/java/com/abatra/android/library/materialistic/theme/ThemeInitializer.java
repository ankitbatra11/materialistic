package com.abatra.android.library.materialistic.theme;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.startup.Initializer;

import java.util.concurrent.Executor;

import bolts.Task;

import static com.abatra.android.wheelie.core.async.bolts.BoltsUtils.getResult;
import static com.abatra.android.wheelie.core.async.bolts.SaferTask.callOn;

abstract public class ThemeInitializer implements Initializer<Integer> {

    Executor backgroundExecutor = Task.BACKGROUND_EXECUTOR;

    @NonNull
    @Override
    public Integer create(@NonNull Context context) {
        callOn(backgroundExecutor, () -> getNightMode(context)).continueOnUiThread(task -> {
            getResult(task).ifPresent(AppCompatDelegate::setDefaultNightMode);
            return null;
        });
        return 0;
    }

    @WorkerThread
    protected abstract int getNightMode(@NonNull Context context);
}

package com.gambitrp.mobile.core;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Permissions {
    public static final String prefs = "GambitMobilePrefs";

    public interface PermissionResponse {
        @RequiresApi(api = Build.VERSION_CODES.M)
        default void request(Window window, String permission) {
            window.getActivity().requestPermissions(new String[] { permission }, 1);
        }

        default void denied() {
        }

        default void disabled() {
        }

        default void allowed() {
        }
    }

    private static boolean isNeedPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    private static boolean isNeedPermission(String permission) {
        if (isNeedPermission()) {
            Window window = Window.getContext();

            int ret = window.checkSelfPermission(permission);

            return ret != PackageManager.PERMISSION_GRANTED;
        }

        return false;
    }

    private static void firstRequestPermission(String permission, boolean first) {
        Window window = Window.getContext();

        SharedPreferences data = window.getSharedPreferences(prefs, MODE_PRIVATE);

        data.edit().putBoolean(permission, first).apply();
    }

    private static boolean isFirstRequestPermission(String permission) {
        Window window = Window.getContext();

        return window.getSharedPreferences(prefs, MODE_PRIVATE).getBoolean(permission, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void check(String permission, PermissionResponse response) {
        Window window = Window.getContext();

        if (isNeedPermission(permission)) {
            if (window.getActivity().shouldShowRequestPermissionRationale(permission)) {
                response.denied();
            } else {
                if (isFirstRequestPermission(permission)) {
                    firstRequestPermission(permission, false);

                    response.request(window, permission);
                } else {
                    response.disabled();
                }
            }
        } else {
            response.allowed();
        }
    }
}

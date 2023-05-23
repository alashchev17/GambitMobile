package com.gambitrp.mobile.core;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Permissions {
    private static final String prefs = "GambitMobilePrefs";

    private static boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    private static boolean shouldAskPermission(String permission) {
        if (shouldAskPermission()) {
            Window window = Window.getContext();

            int permissionResult = window.checkSelfPermission(permission);

            return permissionResult != PackageManager.PERMISSION_GRANTED;
        }

        return false;
    }

    private static void firstTimeAskingPermission(String permission, boolean isFirstTime) {
        Window window = Window.getContext();

        SharedPreferences sharedPreference = window.getSharedPreferences(prefs, MODE_PRIVATE);

        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    private static boolean isFirstTimeAskingPermission(String permission) {
        Window window = Window.getContext();

        return window.getSharedPreferences(prefs, MODE_PRIVATE).getBoolean(permission, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkPermission(String permission, PermissionAskListener listener) {
        Window window = Window.getContext();

        System.out.println("[CLIENT] checkPermission");

        if (shouldAskPermission(permission)) {
            System.out.println("[CLIENT] shouldAskPermission");

            if (window.getActivity().shouldShowRequestPermissionRationale(permission)) {
                System.out.println("[CLIENT] shouldShowRequestPermissionRationale");

                listener.onPermissionPreviouslyDenied();
            } else {
                System.out.println("[CLIENT] else");

                if (isFirstTimeAskingPermission(permission)) {
                    System.out.println("[CLIENT] isFirstTimeAskingPermission");

                    firstTimeAskingPermission(permission, false);

                    listener.onPermissionAsk();
                } else {
                    System.out.println("[CLIENT] else");

                    listener.onPermissionDisabled();
                }
            }
        } else {
            System.out.println("[CLIENT] else");

            listener.onPermissionGranted();
        }
    }

    public interface PermissionAskListener {
        default void onPermissionAsk() {
        }

        default void onPermissionPreviouslyDenied() {
        }

        default void onPermissionDisabled() {
        }

        default void onPermissionGranted() {
        }
    }
}

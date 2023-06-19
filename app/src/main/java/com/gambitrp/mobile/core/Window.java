package com.gambitrp.mobile.core;

import android.app.Application;
import android.content.Context;
import android.webkit.WebView;

import com.gambitrp.mobile.MainActivity;
import com.gambitrp.mobile.core.configs.LauncherConfig;
import com.gambitrp.mobile.network.WebSocket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.ref.WeakReference;

public class Window extends Application {
    private static WeakReference<Context> context;

    private MainActivity activity = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();

        FirebaseApp.initializeApp(context);

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            if (token != null) {
                context.getSharedPreferences(Permissions.prefs, MODE_PRIVATE).edit().putString("androidToken",
                        token).apply();

                System.out.println("[CLIENT] androidToken: " + token);
            }
        });

        Window.context = new WeakReference<>(context);
    }

    @Override
    public void onTerminate() {
        context.clear();

        super.onTerminate();
    }

    public static Window getContext() {
        return (Window) context.get();
    }

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    public boolean javaScriptCall(String function, Object... params) {
        if (activity == null) {
            return false;
        }

        StringBuilder call = new StringBuilder("javascript:" + function + "(");
        int count = 0;

        if (params.length == 0) {
            call.append(");");
        } else {
            for (Object object : params) {
                switch (object.getClass().getSimpleName()) {
                    case "Integer":
                    case "Boolean": call.append(object);
                        break;
                    case "String": call.append("'").append(object).append("'");
                        break;
                }

                if (++count == params.length) {
                    call.append(");");
                } else {
                    call.append(", ");
                }
            }
        }

        System.out.println("[CLIENT] evaluateJavascript: " + call);

        WebView webView = getWebView();
        webView.post(() -> webView.evaluateJavascript(call.toString(), null));

        return true;
    }

    public WebView getWebView() {
        if (activity == null) {
            return null;
        }

        return activity.webView;
    }

    public WebSocket getWebSocket() {
        if (activity == null) {
            return null;
        }

        return activity.webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        activity.webSocket = webSocket;
    }

    public Config<LauncherConfig> getConfig() {
        if (activity == null) {
            return null;
        }

        return activity.launcherConfig;
    }
}

package com.gambitrp.mobile.core;

import com.gambitrp.mobile.MainActivity;
import com.gambitrp.mobile.network.packets.PacketID;

import java.util.Arrays;
import java.util.StringJoiner;

public class Window {
    private static Window instance = null;

    private MainActivity activity = null;

    private Window()
    {
    }

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        if (this.activity != null) return;

        this.activity = activity;
    }

    public boolean javaScriptCall(String function, Object... params) {
        if (activity == null) return false;

        StringBuilder call = new StringBuilder("javascript:" + function + "(");
        int count = 0;

        if (params.length == 0) call.append(");");
        else {
            for (Object object : params) {
                switch (object.getClass().getSimpleName()) {
                    case "Integer":
                    case "Boolean": call.append(object);
                        break;
                    case "String": call.append("'" + object + "'");
                        break;
                }

                if (++count == params.length) call.append(");");
                else call.append(", ");
            }
        }

        activity.getWebView().loadUrl(call.toString());

        return true;
    }

    public static Window getInstance() {
        if (instance == null) {
            Window.instance = new Window();
        }

        return Window.instance;
    }
}

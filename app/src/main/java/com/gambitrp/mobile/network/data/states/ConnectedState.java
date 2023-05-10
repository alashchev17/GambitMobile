package com.gambitrp.mobile.network.data.states;

import static com.gambitrp.mobile.network.data.states.StateType.INIT;

import android.webkit.WebView;

import com.gambitrp.mobile.MainActivity;
import com.gambitrp.mobile.browser.interfaces.JavaScript;
import com.gambitrp.mobile.core.Config;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.configs.LauncherConfig;
import com.gambitrp.mobile.network.data.Session;
import com.gambitrp.mobile.network.packets.PacketID;
import com.gambitrp.mobile.network.packets.handlers.Handler;

public class ConnectedState implements State {
    private final String indexFile = "file:///android_asset/index.html";

    @Override
    public void set(StateType oldStateType) {
        if(oldStateType == INIT) {
            Window window = Window.getInstance();
            window.load(indexFile);

            MainActivity activity = window.getActivity();
            WebView webView = activity.getWebView();

            webView.post(() -> webView.addJavascriptInterface(new JavaScript(activity), "Launcher"));
        }
    }

    @Override
    public void beforeSet(StateType oldStateType) {
    }

    @Override
    public void afterSet(StateType oldStateType) {
        Config<LauncherConfig> cfg = Window.getInstance().getActivity().getConfig();
        LauncherConfig data = cfg.getData();

        if(data.authToken != null) {
            Session.getInstance().setToken(Session.SessionType.AUTH_TOKEN, data.authToken);

            Handler handler = PacketID.AUTH.getHandler();
            handler.setData("session", data.authToken.toString());
            handler.send();
        }
    }
}

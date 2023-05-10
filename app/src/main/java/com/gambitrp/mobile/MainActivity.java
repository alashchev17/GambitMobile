package com.gambitrp.mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.gambitrp.mobile.browser.ChromeClient;
import com.gambitrp.mobile.browser.WebClient;
import com.gambitrp.mobile.core.Config;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.configs.LauncherConfig;
import com.gambitrp.mobile.network.WebSocket;
import com.gambitrp.mobile.network.data.states.StateType;

import java.net.URI;
import java.net.URISyntaxException;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends AppCompatActivity {
    private final String serverAddress = "ws://45.90.219.11:4327/launcher";

    private WebView webView;
    private WebSocket webSocket;
    private Config<LauncherConfig> launcherConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window.getInstance().setActivity(this);

        StateType.INIT.set();

        launcherConfig = new Config<>("launcher", LauncherConfig.class);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new ChromeClient());

        try {
            webSocket = new WebSocket(new URI(serverAddress));
            webSocket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public WebView getWebView() {
        return webView;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public Config<LauncherConfig> getConfig() {
        return launcherConfig;
    }
}

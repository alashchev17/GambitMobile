package com.gambitrp.mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.gambitrp.mobile.browser.ChromeClient;
import com.gambitrp.mobile.browser.WebClient;
import com.gambitrp.mobile.browser.interfaces.JavaScriptInterface;
import com.gambitrp.mobile.core.Logger;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window.getInstance().setActivity(this);
        Logger.getInstance();

        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new ChromeClient());

        webView.loadUrl("file:///android_asset/index.html");

        webView.addJavascriptInterface(new JavaScriptInterface(this), "Launcher");

        try {
            webSocket = new WebSocket(new URI("ws://45.90.219.11:4327/launcher"));

            webSocket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
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
}

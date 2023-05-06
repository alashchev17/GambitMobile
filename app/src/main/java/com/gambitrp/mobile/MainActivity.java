package com.gambitrp.mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import java.net.URI;
import java.net.URISyntaxException;
import com.gambitrp.mobile.browser.ChromeClient;
import com.gambitrp.mobile.browser.interfaces.JavaScriptInterface;
import com.gambitrp.mobile.browser.WebClient;
import com.gambitrp.mobile.network.WebSocket;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private WebSocket webSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        webView = (WebView)findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new ChromeClient());

        webView.loadUrl("file:///android_asset/index.html");

        webView.addJavascriptInterface(new JavaScriptInterface(this), "Gambit");

        try {
            webSocket = new WebSocket(new URI("ws://45.90.219.11:4327/launcher"));

            webSocket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.gambitrp.mobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gambitrp.mobile.browser.ChromeClient;
import com.gambitrp.mobile.browser.WebClient;
import com.gambitrp.mobile.browser.interfaces.JavaScript;
import com.gambitrp.mobile.core.Config;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.configs.LauncherConfig;
import com.gambitrp.mobile.network.WebSocket;
import com.gambitrp.mobile.network.data.states.StateType;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends AppCompatActivity {
    private final String indexFile = "file:///android_asset/index.html";

    private WebView webView;
    private WebSocket webSocket;
    private Config<LauncherConfig> launcherConfig;

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window.getInstance().setActivity(this);

        StateType.INIT.set();

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.setWebViewClient(new WebClient());
        webView.setWebChromeClient(new ChromeClient());

        webView.addJavascriptInterface(new JavaScript(this), "Launcher");
        webView.loadUrl(indexFile);

        launcherConfig = new Config<>("launcher", LauncherConfig.class);
    }

    @Override
    public void onBackPressed() {
        if (!webView.canGoBack()) {
            if (exit) {
                finish();
            } else {
                Toast.makeText(this, "Нажмите кнопку \"Назад\" еще раз, чтобы выйти.", Toast.LENGTH_SHORT).show();

                exit = true;

                new Handler().postDelayed(() -> exit = false, 3000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (webView != null) {
            webView.removeAllViews();
            webView.clearHistory();
            webView.clearCache(true);
            webView.destroy();

            webView = null;
        }
    }

    public WebView getWebView() {
        return webView;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public Config<LauncherConfig> getConfig() {
        return launcherConfig;
    }
}

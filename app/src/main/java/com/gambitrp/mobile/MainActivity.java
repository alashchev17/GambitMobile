package com.gambitrp.mobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.gambitrp.mobile.browser.ChromeClient;
import com.gambitrp.mobile.browser.WebClient;
import com.gambitrp.mobile.browser.interfaces.JavaScript;
import com.gambitrp.mobile.core.Config;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.configs.LauncherConfig;
import com.gambitrp.mobile.network.WebSocket;
import com.gambitrp.mobile.network.data.states.StateType;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {
    private final String indexFile = "file:///android_asset/index.html";

    private final int windowFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    protected Window window;

    public WebView webView;
    public WebSocket webSocket;
    public Config<LauncherConfig> launcherConfig;

    private Boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("[CLIENT] onCreate");

        android.view.Window window = getWindow();
        View view = getWindow().getDecorView();

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        view.setSystemUiVisibility(windowFlags);
        window.setStatusBarColor(Color.TRANSPARENT);

        view.setOnSystemUiVisibilityChangeListener(visibility -> {
            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                view.setSystemUiVisibility(windowFlags);
            }
        });

        this.window = (Window) getApplicationContext();
        this.window.setActivity(this);

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

        webView.addJavascriptInterface(new JavaScript(window.getContext()), "Launcher");
        webView.loadUrl(indexFile);

        launcherConfig = new Config<>("launcher", LauncherConfig.class);
    }

    @Override
    public void onResume() {
        super.onResume();

        System.out.println("[CLIENT] onResume");

        if (webView != null) {
            webView.resumeTimers();
            webView.onResume();
        }
    }

    @Override
    public void onPause() {
        System.out.println("[CLIENT] onPause");

        if (webView != null) {
            webView.onPause();
            webView.pauseTimers();
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.removeAllViews();
            webView.clearHistory();
            webView.clearCache(true);
            webView.pauseTimers();
            webView.destroyDrawingCache();
            webView.destroy();

            webView = null;
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        System.out.println("[CLIENT] onBackPressed");

        if (exit) {
            finish();

            System.exit(0);
        } else {
            Toast.makeText(window, "Нажмите кнопку \"Назад\" еще раз, чтобы выйти.", Toast.LENGTH_SHORT).show();

            exit = true;

            new Handler().postDelayed(() -> exit = false, 3000);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        android.view.Window window = getWindow();
        View view = getWindow().getDecorView();

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        view.setSystemUiVisibility(windowFlags);
        window.setStatusBarColor(Color.TRANSPARENT);
    }
}

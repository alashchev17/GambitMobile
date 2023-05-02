package com.gambitrp.mobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.gambitrp.mobile.function.WebSocket;

import org.json.simple.JSONObject;


public class MainActivity extends AppCompatActivity {
    private WebView client;
    private WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client =(WebView) findViewById(R.id.webview);
        WebSettings webSettings=client.getSettings();
        webSettings.setJavaScriptEnabled(true);
        client.setWebViewClient(new WebClient());
        client.setWebChromeClient(new ChromeClient());
        client.loadUrl("file:///android_asset/index.html");
        client.addJavascriptInterface(new JavaScriptInterface(this), "javafunc");
        try {
            ws = new WebSocket(new URI(
                    "ws://45.90.219.11:4327/launcher"));
            ws.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public class WebClient extends WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view,url,favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onBackPressed(){
        if(client.canGoBack()) {
            client.goBack();
        }
        else{
            super.onBackPressed();
        }
    }
    public class ChromeClient extends WebChromeClient {
        //Обработка событий JS
    }

    public class JavaScriptInterface {
        Context mContext;

        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public boolean auth(String login, String password, Integer code) {
            if(login == null || password == null) {
                return false;
            }
            MessageDigest digest;
            try {
               digest = MessageDigest.getInstance("SHA-256");
            } catch(NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            JSONObject data = new JSONObject();
            data.put("login", login);
            data.put("password", hash);
            if(code != null) {
                data.put("code", code);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "AUTH");
            jsonObject.put("data", data);
            ws.send(jsonObject.toJSONString());
            System.out.println();
            return true;
        }
    }
}

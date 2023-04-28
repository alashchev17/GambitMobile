package com.example.gambitmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gambitmobile.R;

import java.net.URI;
import java.net.URISyntaxException;

import com.example.gambitmobile.function.WebSocket;


public class MainActivity extends AppCompatActivity {



    private WebView client;
    private WebSocket ws;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client =(WebView) findViewById(R.id.webview);
        client.setWebViewClient(new WebViewClient());
        WebSettings webSettings=client.getSettings();
        webSettings.setJavaScriptEnabled(true);
        client.setWebViewClient(new WebClient());
        client.setWebChromeClient(new ChromeClient());
        client.loadUrl("file:///android_asset/index.html");
        client.addJavascriptInterface(new JavaScriptInterface(this), "javafunc");
        try {
            ws = new WebSocket(new URI(
                    "ws://localhost:4327/launcher"));
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
        public boolean testFunc(String test) {
            System.out.println(test);
            return true;
        }
    }
}

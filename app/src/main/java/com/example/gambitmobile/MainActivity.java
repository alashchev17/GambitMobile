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


public class MainActivity extends AppCompatActivity {
    private WebView mywebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mywebView =(WebView) findViewById(R.id.webview);
        mywebView.setWebViewClient(new WebViewClient());
        WebSettings webSettings=mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebView.setWebViewClient(new WebClient());
        mywebView.setWebChromeClient(new ChromeClient());
        mywebView.loadUrl("file:///android_asset/index.html");
        mywebView.addJavascriptInterface(new JavaScriptInterface(this), "javafunc");

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
        if(mywebView.canGoBack()) {
            mywebView.goBack();
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

package com.gambitrp.mobile.browser;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

public class ChromeClient extends WebChromeClient {
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        System.out.println("[CLIENT] onConsoleMessage " + consoleMessage.lineNumber() + " : " + consoleMessage.message());

        return super.onConsoleMessage(consoleMessage);
    }
}

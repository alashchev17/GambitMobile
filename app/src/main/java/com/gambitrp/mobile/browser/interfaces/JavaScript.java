package com.gambitrp.mobile.browser.interfaces;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gambitrp.mobile.network.packets.PacketID;
import com.gambitrp.mobile.network.packets.handlers.Handler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils;

public class JavaScript {
    Context context;

    public JavaScript(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void auth(String login, String password, Integer code) {
        if (login == null || password == null) return;

        Handler handler = PacketID.AUTH.getHandler();
        handler.setData("login", login);
        handler.setData("password", DigestUtils.sha256Hex(password));
        handler.setData("code", code);
        handler.send();
    }
}

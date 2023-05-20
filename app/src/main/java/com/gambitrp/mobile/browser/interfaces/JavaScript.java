package com.gambitrp.mobile.browser.interfaces;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.webkit.JavascriptInterface;

import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.packets.PacketID;
import com.gambitrp.mobile.network.packets.handlers.Handler;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.digest.DigestUtils;

public class JavaScript {
    Context context;

    public JavaScript(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void auth(String login, String password, boolean remember, int code) {
        if (login == null || password == null) {
            return;
        }

        Handler handler = PacketID.AUTH.getHandler();
        handler.setData("login", login);
        handler.setData("password", DigestUtils.sha256Hex(password));
        handler.setData("save", remember);
        handler.setData("code", code);
        handler.send(true);
    }

    @JavascriptInterface
    public String getClipboardData() {
        ClipboardManager clipboard = (ClipboardManager) Window.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = clipboard.getPrimaryClip();
        ClipData.Item item = data.getItemAt(0);

        return item.getText().toString();
    }
}

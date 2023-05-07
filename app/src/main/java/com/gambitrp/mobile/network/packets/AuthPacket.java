package com.gambitrp.mobile.network.packets;

import org.json.JSONException;
import org.json.simple.JSONObject;
import com.gambitrp.mobile.core.Window;

public class AuthPacket implements Packet {
    @Override
    public void response(JSONObject data) {
        int type;
        if(data.get("token") == "") {
            data.clear();
            type = 1;
        } else {
            data.remove("token");
            type = 2;
        }
        Window.getInstance().javaScriptCall("v.xz", type, data.toJSONString()); // Изменю функцию потом
    }

    @Override
    public void error(JSONObject data) {
        System.out.println("[CLIENT] " + data.get("error_message"));;
    }
}

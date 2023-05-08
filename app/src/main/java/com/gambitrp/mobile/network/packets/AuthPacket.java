package com.gambitrp.mobile.network.packets;

import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.data.Session;

import org.json.simple.JSONObject;

import java.util.UUID;

public class AuthPacket implements Packet {
    private final int typeTwoFactor = 1;
    private final int typeAuthorized = 2;

    @Override
    public void response(JSONObject data) {
        int type;
        UUID token = UUID.fromString((String) data.get("token"));

        Session.getInstance().setToken(token);

        if (token == null) {
            data.clear();

            type = typeTwoFactor;
        } else {
            data.remove("token");

            type = typeAuthorized;
        }

        Window.getInstance().javaScriptCall("v.launcherResponse", type, data.toJSONString());
    }

    @Override
    public void error(PacketError error) {
        Window.getInstance().javaScriptCall("v.launcherError", error.getValue(), error.getDescription());

        System.out.println("[CLIENT] error: " + error);
    }
}

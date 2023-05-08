package com.gambitrp.mobile.network.packets;

import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.data.Session;

import org.json.simple.JSONObject;

public class AuthPacket implements Packet {
    private final int typeTwoFactor = 1;
    private final int typeAuthorized = 2;

    @Override
    public void response(JSONObject data) {
        int type;
        String token = (String) data.get("token");

        Session.getInstance().setToken(token);

        System.out.println("[CLIENT] token: " + token);

        if (token == null || token.isEmpty()) {
            data.clear();

            type = typeTwoFactor;
        } else {
            data.remove("token");

            type = typeAuthorized;
        }

        Window.getInstance().javaScriptCall("v.xz", type, data.toJSONString());
    }

    @Override
    public void error(PacketError error) {
        System.out.println("[CLIENT] description: " + error.getDescription());
    }
}

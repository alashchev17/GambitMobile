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

        String signatureToken = (String) data.get("token");
        String authToken = (String) data.get("session_token");

        Session.getInstance().setToken(Session.SessionType.SIGNATURE_TOKEN, signatureToken);
        Session.getInstance().setToken(Session.SessionType.AUTH_TOKEN, authToken);

        if (signatureToken == null || signatureToken.isBlank()) {
            data.clear();

            type = typeTwoFactor;
        } else {
            if (Window.getContext().getConfig().getData().gameVersion.isBlank()) {
                data.remove("launcher_update");
            }

            data.remove("two_factor");
            data.remove("token");
            data.remove("session_token");

            type = typeAuthorized;
        }

        Window.getContext().javaScriptCall("v.launcherResponse", type, data.toJSONString());
    }

    @Override
    public void error(PacketError error) {
        Window.getContext().javaScriptCall("v.launcherError", error.getValue(), error.getDescription());

        System.out.println("[CLIENT] error: " + error);
    }
}

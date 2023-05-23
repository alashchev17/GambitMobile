package com.gambitrp.mobile.network.packets;

import static com.gambitrp.mobile.network.packets.PacketError.SESSION_EXPIRED;
import static com.gambitrp.mobile.network.packets.PacketError.USERNAME_OR_SESSION_NULL;

import com.gambitrp.mobile.core.Config;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.configs.LauncherConfig;
import com.gambitrp.mobile.network.data.Session;

import org.json.simple.JSONObject;

import java.util.UUID;

public class AuthPacket implements Packet {
    private final int typeTwoFactor = 1;
    private final int typeAuthorized = 2;

    @Override
    public void response(JSONObject data) {
        int type;

        String signatureToken = (String) data.get("token");
        String authToken = (String) data.get("session_token");

        Session.getInstance().setToken(Session.SessionType.SIGNATURE_TOKEN, signatureToken, false);
        Session.getInstance().setToken(Session.SessionType.AUTH_TOKEN, authToken, true);

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
        Config<LauncherConfig> cfg = Window.getContext().getConfig();

        switch (error) {
            case USERNAME_OR_SESSION_NULL:
            case SESSION_EXPIRED: {
                LauncherConfig data = cfg.getData();

                if(data.authToken != null || error == SESSION_EXPIRED) {
                    Session.getInstance().setToken(Session.SessionType.AUTH_TOKEN, (UUID) null, true);

                    Window.getContext().javaScriptCall("v.displaysInit");
                } else {
                    Window.getContext().javaScriptCall("v.launcherError",
                            USERNAME_OR_SESSION_NULL.getValue(),
                            USERNAME_OR_SESSION_NULL.getDescription());
                }

                break;
            }
            default: {
                Window.getContext().javaScriptCall("v.launcherError", error.getValue(), error.getDescription());
            }
        }

        System.out.println("[CLIENT] error: " + error);
    }
}

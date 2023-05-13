package com.gambitrp.mobile.network.data;

import com.gambitrp.mobile.core.Config;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.configs.LauncherConfig;

import java.util.UUID;

public class Session {
    private static Session instance = null;

    private UUID signatureToken;
    private UUID authToken;

    public enum SessionType {
        SIGNATURE_TOKEN,
        AUTH_TOKEN
    }

    private Session()
    {
    }

    public String getToken(SessionType type) {
        switch (type) {
            case SIGNATURE_TOKEN: return signatureToken != null ? signatureToken.toString() : "";
            case AUTH_TOKEN: return authToken != null ? authToken.toString() : "";
        }

        return null;
    }

    public void setToken(SessionType type, String token) {
        if (token == null || token.isBlank()) {
            return;
        }

        switch (type) {
            case SIGNATURE_TOKEN: signatureToken = UUID.fromString(token);
                break;
            case AUTH_TOKEN: {
                authToken = UUID.fromString(token);

                Config<LauncherConfig> cfg = Window.getInstance().getConfig();

                cfg.getData().authToken = authToken;
                cfg.saveData();

                break;
            }
        }
    }

    public void setToken(SessionType type, UUID token) {
        if (token == null) {
            return;
        }

        switch (type) {
            case SIGNATURE_TOKEN: signatureToken = token;
                break;
            case AUTH_TOKEN: authToken = token;
                break;
        }
    }

    public static Session getInstance() {
        if (instance == null) {
            Session.instance = new Session();
        }

        return Session.instance;
    }
}

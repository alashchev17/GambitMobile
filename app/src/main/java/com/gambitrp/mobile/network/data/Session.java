package com.gambitrp.mobile.network.data;

import java.util.UUID;

public class Session {
    private static Session instance = null;

    private UUID token;

    private Session()
    {
    }

    public String getToken() {
        return token != null ? token.toString() : "";
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public static Session getInstance() {
        if (instance == null) {
            Session.instance = new Session();
        }

        return Session.instance;
    }
}

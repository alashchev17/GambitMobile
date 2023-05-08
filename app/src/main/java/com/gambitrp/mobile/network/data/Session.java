package com.gambitrp.mobile.network.data;

public class Session {
    private static Session instance = null;

    private String token;

    public String getToken() {
        return token;
    }

    private Session()
    {
    }

    public void setToken(String token) {
        if (token == null) token = "";

        this.token = token;
    }

    public static Session getInstance() {
        if (instance == null) {
            Session.instance = new Session();
        }

        return Session.instance;
    }
}

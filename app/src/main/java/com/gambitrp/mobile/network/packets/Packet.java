package com.gambitrp.mobile.network.packets;

import org.json.simple.JSONObject;

public interface Packet {
    void response(JSONObject data);
    void error(JSONObject data);
}

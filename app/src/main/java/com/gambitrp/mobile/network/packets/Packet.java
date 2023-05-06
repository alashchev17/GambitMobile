package com.gambitrp.mobile.network.packets;

import org.json.simple.JSONObject;

public interface Packet {
    void Response(JSONObject data);
    void Error(JSONObject data);
}

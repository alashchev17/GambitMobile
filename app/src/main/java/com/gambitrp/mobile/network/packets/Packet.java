package com.gambitrp.mobile.network.packets;

import com.gambitrp.mobile.network.packets.handlers.Handler;

import org.json.simple.JSONObject;

public interface Packet {
    void response(JSONObject data);
    void error(PacketError error);

    default void clearHandler(Handler handler) {
        handler.data.clear();
    }
}

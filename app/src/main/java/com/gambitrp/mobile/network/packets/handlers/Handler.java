package com.gambitrp.mobile.network.packets.handlers;

import com.gambitrp.mobile.network.packets.PacketID;

import java.util.HashMap;
import java.util.Map;

public interface Handler {
    Map<String, Object> map = new HashMap<>();

    PacketID getPacket();

    default void setData(String field, Object value) {
        map.put(field, value);
    }

    default Map<String, Object> getData() {
        return map;
    }

    default void send() {
        HandlerSender.getInstance().send(this);
    }
}

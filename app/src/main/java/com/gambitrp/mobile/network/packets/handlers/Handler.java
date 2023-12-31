package com.gambitrp.mobile.network.packets.handlers;

import com.gambitrp.mobile.network.packets.PacketID;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public interface Handler {
    Map<String, Object> map = new HashMap<>();
    Map<String, Boolean> data = new HashMap<>();

    PacketID getPacket();
    JSONObject beforeSend();

    default void setData(String field, Object value) {
        map.put(field, value);
    }

    default Map<String, Object> getData() {
        return map;
    }

    default void send(boolean response) {
        if (data.get("awaitResponse") != null) {
            map.clear();

            return;
        }

        data.put("awaitResponse", true);

        HandlerSender.getInstance().send(this);
    }
}

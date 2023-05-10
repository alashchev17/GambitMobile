package com.gambitrp.mobile.network.packets;

import java.util.HashMap;
import java.util.Map;

public enum PacketError {
    NULL_DATA(101, "NULL_DATA"),
    NULL_SESSION(102, "NULL_SESSION"),
    SESSION_ACTIVE(103, "SESSION_ACTIVE"),
    INVALID_PASSWORD(104, "INVALID_PASSWORD"),
    INVALID_TWO_FACTOR(105, "INVALID_TWO_FACTOR"),
    SESSION_END_LIFE(106, "SESSION_END_LIFE"),
    ERROR_SEND_CODE(107, "ERROR_SEND_CODE");

    private final int id;
    private final String description;
    private static final Map<Integer, PacketError> map = new HashMap<>();

    PacketError(int id, String description) {
        this.id = id;
        this.description = description;
    }

    static {
        for (PacketError packetError : PacketError.values()) {
            map.put(packetError.id, packetError);
        }
    }

    public static PacketError valueOf(int id) {
        return map.get(id);
    }

    public int getValue() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}

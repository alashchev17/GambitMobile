package com.gambitrp.mobile.network.packets;

import java.util.HashMap;
import java.util.Map;

public enum PacketError {
    DATA_ERROR_1(101, "DATA_ERROR_1"),
    DATA_ERROR_2(102, "DATA_ERROR_2"),
    NICKNAME_NOT_FOUND(103, "NICKNAME_NOT_FOUND"),
    INVALID_PASSWORD(104, "INVALID_PASSWORD"),
    INVALID_TWO_FACTOR(105, "INVALID_TWO_FACTOR");

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

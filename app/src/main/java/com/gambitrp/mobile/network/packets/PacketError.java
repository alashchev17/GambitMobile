package com.gambitrp.mobile.network.packets;

import java.util.HashMap;
import java.util.Map;

public enum PacketError {
    USERNAME_OR_PASSWORD_NULL(101, "USERNAME_OR_PASSWORD_NULL"),
    USERNAME_OR_SESSION_NULL(102, "USERNAME_OR_SESSION_NULL"),
    SESSION_ALREADY_ACTIVE(103, "SESSION_ALREADY_ACTIVE"),
    INVALID_PASSWORD(104, "INVALID_PASSWORD"),
    INVALID_TWO_FACTOR_CODE(105, "INVALID_TWO_FACTOR_CODE"),
    SESSION_EXPIRED(106, "SESSION_EXPIRED"),
    ERROR_SEND_TWO_FACTOR_CODE(107, "ERROR_SEND_TWO_FACTOR_CODE");

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

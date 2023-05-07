package com.gambitrp.mobile.network.packets;

import java.util.HashMap;
import java.util.Map;

public enum PacketID {
    AUTH(1, new AuthPacket());

    private final int id;
    private final Packet packet;
    private static final Map<Integer, PacketID> map = new HashMap<>();

    PacketID(int id, Packet packet) {
        this.id = id;
        this.packet = packet;
    }

    static {
        for (PacketID packetID : PacketID.values()) {
            map.put(packetID.id, packetID);
        }
    }

    public static PacketID valueOf(int id) {
        return map.get(id);
    }

    public int getValue() {
        return id;
    }

    public Packet getPacket() {
        return packet;
    }
}

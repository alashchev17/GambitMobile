package com.gambitrp.mobile.network.packets;

import com.gambitrp.mobile.network.packets.handlers.AuthHandler;
import com.gambitrp.mobile.network.packets.handlers.Handler;

import java.util.HashMap;
import java.util.Map;

public enum PacketID {
    AUTH(1, new AuthPacket(), new AuthHandler());

    private final int id;
    private final Packet packet;
    private final Handler handler;
    private static final Map<Integer, PacketID> map = new HashMap<>();

    PacketID(int id, Packet packet, Handler handler) {
        this.id = id;
        this.packet = packet;
        this.handler = handler;
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

    public Handler getHandler() {
        return handler;
    }
}

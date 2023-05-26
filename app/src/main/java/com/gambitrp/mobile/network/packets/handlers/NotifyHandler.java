package com.gambitrp.mobile.network.packets.handlers;

import com.gambitrp.mobile.network.packets.PacketID;

import org.json.simple.JSONObject;

public class NotifyHandler implements Handler {
    @Override
    public PacketID getPacket() {
        return PacketID.NOTIFY;
    }

    @Override
    public JSONObject beforeSend() {
        return null;
    }
}

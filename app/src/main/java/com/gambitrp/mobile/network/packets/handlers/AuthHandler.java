package com.gambitrp.mobile.network.packets.handlers;

import com.gambitrp.mobile.network.packets.PacketID;

public class AuthHandler implements Handler {
    @Override
    public PacketID getPacket() {
        return PacketID.AUTH;
    }
}

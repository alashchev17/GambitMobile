package com.gambitrp.mobile.network.packets.handlers;

import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.packets.PacketID;

import org.json.simple.JSONObject;

public class AuthHandler implements Handler {
    @Override
    public PacketID getPacket() {
        return PacketID.AUTH;
    }

    @Override
    public JSONObject beforeSend() {
        JSONObject data = new JSONObject();
        data.put("version", Window.getInstance().getConfig().getData().gameVersion);

        return data;
    }
}

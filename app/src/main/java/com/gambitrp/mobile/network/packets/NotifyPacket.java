package com.gambitrp.mobile.network.packets;

import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.notifications.Push;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class NotifyPacket implements Packet {
    private final int typeNotify = 3;

    @Override
    public void response(JSONObject data) {
        Boolean push = (Boolean) data.get("push");
        JSONArray notice = (JSONArray) data.get("messages");

        if (notice == null) {
            return;
        }

        Window.getContext().javaScriptCall("v.launcherResponse", typeNotify, notice.toJSONString());

        if (Boolean.TRUE.equals(push)) {
            JSONObject msg = (JSONObject) notice.get(notice.size() - 1);

            Push.send((String) msg.get("character_name"), (String) msg.get("text"));
        }
    }

    @Override
    public void error(PacketError error) {
    }
}

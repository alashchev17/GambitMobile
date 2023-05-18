package com.gambitrp.mobile.network.packets.handlers;

import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.WebSocket;
import com.gambitrp.mobile.network.data.Session;

import org.json.simple.JSONObject;

public class HandlerSender {
    private static HandlerSender instance = null;

    private WebSocket webSocket;
    private final int launcherType = 1;

    private HandlerSender()
    {
        if (webSocket == null) {
            webSocket = Window.getContext().getWebSocket();
        }
    }

    public void send(Handler handler) {
        if (webSocket == null) {
            return;
        }

        JSONObject packet = new JSONObject();
        JSONObject data = new JSONObject(handler.getData());
        JSONObject addData = handler.beforeSend();

        if (addData != null) {
            for (Object key : addData.keySet()) {
                data.put(key, addData.get(key));
            }
        }

        packet.put("type", handler.getPacket().getValue());
        packet.put("launcher", launcherType);
        packet.put("data", data);
        packet.put("token", Session.getInstance().getToken(Session.SessionType.SIGNATURE_TOKEN));

        webSocket.send(packet.toJSONString());

        System.out.println("[CLIENT] send: " + packet);

        handler.map.clear();
    }

    public static HandlerSender getInstance() {
        if (instance == null) {
            HandlerSender.instance = new HandlerSender();
        }

        return HandlerSender.instance;
    }
}

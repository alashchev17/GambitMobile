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
        if (webSocket == null) webSocket = Window.getInstance().getActivity().getWebSocket();
    }

    public void send(Handler handler) {
        if (webSocket == null) return;

        JSONObject data = new JSONObject();
        data.put("type", handler.getPacket().getValue());
        data.put("launcher", launcherType);
        data.put("data", new JSONObject(handler.getData()));
        data.put("token", Session.getInstance().getToken());

        webSocket.send(data.toJSONString());

        System.out.println("[CLIENT] send: " + data);

        handler.packetSent();
        handler.map.clear();
    }

    public static HandlerSender getInstance() {
        if (instance == null) {
            HandlerSender.instance = new HandlerSender();
        }

        return HandlerSender.instance;
    }
}

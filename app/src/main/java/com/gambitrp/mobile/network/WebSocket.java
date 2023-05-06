package com.gambitrp.mobile.network;

import com.gambitrp.mobile.network.packets.AuthPacket;
import com.gambitrp.mobile.network.packets.Packet;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class WebSocket extends WebSocketClient {
    private final Map<PacketsID, Packet> packetsMap = new HashMap<>();

    public enum PacketsID {
        NULL,
        AUTH
    }

    public WebSocket(URI serverURI) {
        super(serverURI);

        packetsMap.put(PacketsID.AUTH, new AuthPacket());
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
    }

    @Override
    public void onMessage(String message) {
        JSONParser jsonParser = new JSONParser();
        Object object;
        PacketsID packet;

        try {
            object = jsonParser.parse(message);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = (JSONObject) object;

        try {
            packet = (PacketsID) jsonObject.get("type");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if (packetsMap.containsKey(packet)) {
            JSONObject data;

            try {
                data = (JSONObject) jsonObject.get("data");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            packetsMap.get(packet).Response(data);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {
    }
}

package com.gambitrp.mobile.network;

import com.gambitrp.mobile.network.packets.AuthPacket;
import com.gambitrp.mobile.network.packets.Packet;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.simple.JSONObject;
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
        System.out.println("[CLIENT] open connection socket");
        send("{\"type\": 1, \"data\": {\"login\": \"Volk\", \"password\": \"123123\"}}");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("[CLIENT] socket message: " + message);
        JSONParser jsonParser = new JSONParser();
        Object object;
        PacketsID packet;

        try {
            object = jsonParser.parse(message);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = (JSONObject) object;

        packet = PacketsID.values()[Integer.parseInt(jsonObject.get("type").toString())]; // Говно, нужно будет красивее сделать :D

        if (packetsMap.containsKey(packet)) {
            JSONObject data;

            data = (JSONObject) jsonObject.get("response");

            if(data.get("error") != null) {
                packetsMap.get(packet).Error(data);
                return;
            }
            packetsMap.get(packet).Response(data);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[CLIENT] Close connection + " + code + ". Message: " + reason);
    }

    @Override
    public void onError(Exception ex) {
    }
}

package com.gambitrp.mobile.network;

import com.gambitrp.mobile.network.packets.PacketID;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;

public class WebSocket extends WebSocketClient {
    public WebSocket(URI serverURI) {
        super(serverURI);
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
        PacketID packet;

        try {
            object = jsonParser.parse(message);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = (JSONObject) object;
        if (jsonObject == null) return;

        Long id = (Long) jsonObject.get("type");
        if (id == null) return;

        packet = PacketID.valueOf(id.intValue());

        if (packet != null) {
            JSONObject data;

            data = (JSONObject) jsonObject.get("response");
            if (data == null) return;

            if(data.get("error") != null) {
                packet.getPacket().error(data);

                return;
            }

            packet.getPacket().response(data);
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
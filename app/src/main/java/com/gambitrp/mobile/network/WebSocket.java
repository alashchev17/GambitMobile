package com.gambitrp.mobile.network;

import com.gambitrp.mobile.browser.interfaces.JavaScript;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.packets.PacketError;
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

        //send("{\"type\": 1, \"data\": {\"login\": \"testlc\", \"password\": \"ecd71870d1963316a97e3ac3408c9835ad8cf0f3c1bc703527c30265534f75ae\"}}");

        new JavaScript(Window.getInstance().getActivity()).auth("testlc", "test123", 0);
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

            Long error = (Long) data.get("error");

            if(error != null) {
                packet.getPacket().error(PacketError.valueOf(error.intValue()));

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

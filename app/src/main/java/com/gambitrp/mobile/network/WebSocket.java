package com.gambitrp.mobile.network;

import com.gambitrp.mobile.network.data.states.StateType;
import com.gambitrp.mobile.network.packets.PacketError;
import com.gambitrp.mobile.network.packets.PacketID;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.URI;
import java.util.concurrent.Semaphore;

public class WebSocket extends WebSocketClient {
    private final Semaphore semaphoreMsg = new Semaphore(1);

    public WebSocket(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        StateType.CONNECTED.set();
    }

    @Override
    public void onMessage(String message) {
        System.out.println("[CLIENT] onMessage: " + message);

        new Thread(() -> {
            try {
                semaphoreMsg.acquire();

                JSONParser jsonParser = new JSONParser();
                Object object;
                PacketID packet;

                try {
                    object = jsonParser.parse(message);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                JSONObject jsonObject = (JSONObject) object;
                if (jsonObject == null) {
                    return;
                }

                Long id = (Long) jsonObject.get("type");
                if (id == null) {
                    return;
                }

                packet = PacketID.valueOf(id.intValue());

                if (packet != null) {
                    JSONObject data;

                    data = (JSONObject) jsonObject.get("response");
                    if (data == null) {
                        return;
                    }

                    Long error = (Long) data.get("error");

                    packet.getPacket().clearHandler(packet.getHandler());

                    if(error != null) {
                        packet.getPacket().error(PacketError.valueOf(error.intValue()));

                        return;
                    }

                    packet.getPacket().response(data);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphoreMsg.release();
            }
        }).start();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("[CLIENT] onClose: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("[CLIENT] onError: " + ex);
    }
}

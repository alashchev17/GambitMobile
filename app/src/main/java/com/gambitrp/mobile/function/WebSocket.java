package com.gambitrp.mobile.function;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most
 * important callbacks are overloaded.
 */
public class WebSocket extends WebSocketClient {

    public WebSocket(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public WebSocket(URI serverURI) {
        super(serverURI);
    }

    public WebSocket(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("ping");
        System.out.println("----------------------opened connection");
    }

    @Override
    public void onMessage(String message)  {
        JSONParser parser = new JSONParser();
        Object obj;
        try {
           obj = parser.parse(message);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObj = (JSONObject) obj;
        JSONObject  data = (JSONObject) jsonObj.get("data");
        switch(jsonObj.get("type").toString()) {
            case "AUTH":
                if (data.get("error") != null) {
                    switch(data.get("error").toString()) {
                        case "101":
                            break;
                        case "102":
                            break;
                        case "103":
                            break;
                        case "104":
                            break;
                        case "105":
                            break;
                    }
                    return;
                }
                if(data.get("token") == "") {
                    //Двухфакторка
                }
                //Пройдено
                break;
        }
        System.out.println("-------------received: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "----------------Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }


}
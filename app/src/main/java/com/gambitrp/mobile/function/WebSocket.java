package com.gambitrp.mobile.function;

import android.os.Build;

import com.gambitrp.mobile.MainActivity;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

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

    private String getErrorMessage(int err) {
    Map<Integer, String> errorMessages = Map.of(
        101, "Произошла ошибка данных. Повторите попытку",
        102, "Произошла ошибка данных. Повторите попытку",
        103, "Введёный ник не найден.",
        104, "Неверный пароль",
        105, "Неверный код двухфакторной аунтификации"
    );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return errorMessages.getOrDefault(err, "");
        }
        return null;
    }

    @Override
    public void onMessage(String message)  {
        System.out.println("-------------received: " + message);
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
                    String error = "";
                    int err = Integer.parseInt(data.get("error").toString());
                    error = getErrorMessage(err);
                    JSONObject response = new JSONObject();
                    response.put("type", "auth");
                    response.put("error_message", error);
                    response.put("error", err);
                    MainActivity.JavaScriptCall("v.error", response.toJSONString());
                    return;
                }
                if(data.get("token") == "") {
                    JSONObject response = new JSONObject();
                    response.put("type", "auth");
                    response.put("page", "google");
                    MainActivity.JavaScriptCall("v.confirm", response.toJSONString());
                    return;
                }
                JSONObject response = new JSONObject();
                response.put("type", "auth");
                response.put("page", "main");
                response.put("characters", data.get("characters"));
                response.put("login", data.get("login"));
                MainActivity.JavaScriptCall("v.confirm", response.toJSONString());
                MainActivity.Token = data.get("token").toString();

                break;
        }
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
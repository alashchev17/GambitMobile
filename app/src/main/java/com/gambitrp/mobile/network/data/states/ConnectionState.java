package com.gambitrp.mobile.network.data.states;

import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.network.WebSocket;

import java.net.URI;
import java.net.URISyntaxException;

public class ConnectionState implements State
{
    private final String serverAddress = "ws://45.90.219.11:4327/launcher";

    @Override
    public void set(StateType oldStateType) {
        Window window = Window.getInstance();
        window.javaScriptCall("v.displaysInit");

        try {
            WebSocket webSocket = new WebSocket(new URI(serverAddress));
            webSocket.connect();

            window.getActivity().setWebSocket(webSocket);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

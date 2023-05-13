package com.gambitrp.mobile.network.data.states;

import com.gambitrp.mobile.core.Config;
import com.gambitrp.mobile.core.Window;
import com.gambitrp.mobile.core.configs.LauncherConfig;
import com.gambitrp.mobile.network.data.Session;
import com.gambitrp.mobile.network.packets.PacketID;
import com.gambitrp.mobile.network.packets.handlers.Handler;

public class ConnectedState implements State {
    @Override
    public void set(StateType oldStateType) {
        Config<LauncherConfig> cfg = Window.getInstance().getConfig();
        LauncherConfig data = cfg.getData();

        if(data.authToken != null) {
            Session.getInstance().setToken(Session.SessionType.AUTH_TOKEN, data.authToken);

            Handler handler = PacketID.AUTH.getHandler();
            handler.setData("session", data.authToken.toString());
            handler.send();
        } else {
            Window.getInstance().javaScriptCall("v.displaysInit");
        }
    }
}

package com.gambitrp.mobile.network.data.states;

import com.gambitrp.mobile.core.Logger;

public class InitState implements State {
    @Override
    public void set(StateType oldStateType) {
        Logger.getInstance().log(Logger.LoggerType.INFO, "Launcher initialization...");
    }
}

package com.gambitrp.mobile.network.data.states;

public interface State {
    void set(StateType oldStateType);
    void beforeSet(StateType oldStateType);
    void afterSet(StateType oldStateType);
}

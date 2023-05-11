package com.gambitrp.mobile.network.data.states;

import java.util.HashMap;
import java.util.Map;

public enum StateType {
    INIT(1, new InitState()),
    CONNECTION(2, new ConnectionState()),
    CONNECTED(3, new ConnectedState());

    private final int id;
    private final State state;
    private static final Map<Integer, StateType> map = new HashMap<>();

    private static StateType oldStateType = null;

    StateType(int id, State state) {
        this.id = id;
        this.state = state;
    }

    static {
        for (StateType type : StateType.values()) {
            map.put(type.id, type);
        }
    }

    public static StateType valueOf(int id) {
        return map.get(id);
    }

    public int getValue() {
        return id;
    }

    public void set() {
        System.out.println("[CLIENT] set: " + this);
        System.out.println("[CLIENT] oldStateType: " + oldStateType);

        state.set(oldStateType);

        oldStateType = this;
    }

    public static StateType getOldStateType() {
        return oldStateType;
    }
}

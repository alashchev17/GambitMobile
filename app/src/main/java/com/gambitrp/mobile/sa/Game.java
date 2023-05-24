package com.gambitrp.mobile.sa;

public class Game {
    private static Game instance = null;

    public void start() {
    }

    public static Game getInstance() {
        if (instance == null) {
            Game.instance = new Game();
        }

        return Game.instance;
    }
}

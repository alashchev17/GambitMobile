package com.gambitrp.mobile.sa;

import android.content.Intent;

import com.gambitrp.mobile.core.Window;

public class Game {
    private static Game instance = null;

    public void start() {
        Window window = Window.getContext();

        Intent intent = new Intent(Window.getContext(), GameActivity.class);
        window.getActivity().startActivity(intent);
    }

    public static Game getInstance() {
        if (instance == null) {
            Game.instance = new Game();
        }

        return Game.instance;
    }
}

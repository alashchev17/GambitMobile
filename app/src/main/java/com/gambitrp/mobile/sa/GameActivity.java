package com.gambitrp.mobile.sa;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.loadLibrary("ImmEmulatorJ");
        System.loadLibrary("SCAnd");
        System.loadLibrary("GTASA");
    }
}
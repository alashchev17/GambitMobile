package com.gambitrp.mobile.core;

public class Logger {
    private static Logger instance = null;

    private final static String logName = "launcher.log";

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            Logger.instance = new Logger();
        }

        return Logger.instance;
    }
}

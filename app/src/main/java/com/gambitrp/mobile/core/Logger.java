package com.gambitrp.mobile.core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Date;

public class Logger implements AutoCloseable {
    private static Logger instance = null;

    private final static String logName = "launcher.log";
    private final FileOutputStream handle;

    public enum LoggerType {
        INFO,
        WARNING,
        ERROR,
        DEBUG
    }

    private Logger() {
        String path = Window.getInstance().getActivity().getFilesDir().getAbsolutePath() + "/" + logName;

        System.out.println("[CLIENT] path: " + path);

        try {
            handle = new FileOutputStream(path, false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void log(LoggerType type, String format, Object... params) {
        if (handle == null) {
            return;
        }

        MessageFormat row = new MessageFormat(format);
        row.format(params);

        Date date = new Date();
        String data = "[" + date + "] [" + type + "] " + format + System.lineSeparator();

        try {
            handle.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            Logger.instance = new Logger();
        }

        return Logger.instance;
    }

    @Override
    public void close() throws Exception {
        if (handle != null) {
            handle.close();
        }
    }
}

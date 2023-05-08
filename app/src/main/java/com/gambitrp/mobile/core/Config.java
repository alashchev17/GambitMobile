package com.gambitrp.mobile.core;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public class Config<T> {
    private final String path = Window.getInstance().getActivity().getFilesDir().getAbsolutePath() + "/configs";
    private final String name;
    private final String pathFile;

    private final T data;

    public Config(String name, Class<T> type) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();

        String pathFile = path + "/" + name + ".conf";
        File file = new File(pathFile);

        this.name = name;
        this.pathFile = pathFile;

        System.out.println("[CLIENT] pathFile: " + pathFile);

        if (file.exists()) {
            try {
                FileInputStream handle = new FileInputStream(pathFile);
                int value;
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                while ((value = handle.read()) > -1) bytes.write(value);

                data = new Gson().fromJson(bytes.toString(), type);

                handle.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                data = type.getDeclaredConstructor().newInstance();

                FileOutputStream handle = new FileOutputStream(pathFile, false);
                handle.write(new Gson().toJson(data).getBytes(StandardCharsets.UTF_8));
                handle.close();
            } catch (IOException | NoSuchMethodException | IllegalAccessException |
                     InstantiationException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return pathFile;
    }

    public T getData() {
        return data;
    }
}

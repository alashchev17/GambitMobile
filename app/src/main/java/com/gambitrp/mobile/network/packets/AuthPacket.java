package com.gambitrp.mobile.network.packets;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class AuthPacket implements Packet {
    @Override
    public void response(JSONObject data) {
        if(data.get("token") == "") {
            System.out.println("[CLIENT] 2FA");
            //Отправка JS на двухфакторную аунтификацию
            return;
        }
        //Пройдена аунтификация, передаём JS все нужные параметры и переводим на главную страницу
        System.out.println("[CLIENT] test");
    }

    @Override
    public void error(JSONObject data) {
        System.out.println("[CLIENT] " + data.get("error_message"));;
    }
}

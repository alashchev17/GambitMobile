package com.gambitrp.mobile.network.packets;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class AuthPacket implements Packet {
    @Override
    public void Response(JSONObject data) {
        if(data.get("token") == "") {
            System.out.println("2FA");
            //Отправка JS на двухфакторную аунтификацию
            return;
        }
        //Пройдена аунтификация, передаём JS все нужные параметры и переводим на главную страницу

    }

    public void Error(JSONObject data) {
        System.out.println(data.get("error_message"));;
    }
}

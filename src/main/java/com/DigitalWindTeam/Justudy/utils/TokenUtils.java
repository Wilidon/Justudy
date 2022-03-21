package com.DigitalWindTeam.Justudy.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private static final byte[] secret = "DigitalWindSecretKey0ZxmnhfeHafesfdja".getBytes();

    // Возвращаем новый токен
    public static String getToken(int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id); // uid
        map.put("sta", new Date().getTime()); // время генерации
        map.put("exp", new Date().getTime() + (1000 * 60 * 60 * 24)); // время истечения
        String token = null;
        try {
            token = TokenUtils.creatToken(map);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return token;
    }

    // Генерируем токен
    public static String creatToken(Map<String,Object> payloadMap) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        // Создать полезную нагрузку
        Payload payload = new Payload(new JSONObject(payloadMap));

        // Объединяем голову и груз вместе
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        // Создать ключ
        JWSSigner jwsSigner = new MACSigner(secret);

        //подпись
        jwsObject.sign(jwsSigner);

        // Генерируем токен
        return jwsObject.serialize();
    }
    public static Map<String,Object> checkToken(String token) throws ParseException, JOSEException {
        // Разобрать токен

        JWSObject jwsObject = JWSObject.parse(token);

        // Получить груз
        Payload payload=jwsObject.getPayload();

        // Создать ключ разблокировки
        JWSVerifier jwsVerifier = new MACVerifier(secret);

        Map<String, Object> resultMap = new HashMap<>();
        // Токен суждения
        if (jwsObject.verify(jwsVerifier)) {
            resultMap.put("status", 0); // ok
            // Данные полезной нагрузки анализируются в объекте json.
            JSONObject jsonObject = (JSONObject) payload.toJSONObject();
            resultMap.put("data", jsonObject);

            // Определяем, истек ли токен
            if (jsonObject.containsKey("exp")) {
                Long expTime = Long.valueOf(jsonObject.get("exp").toString());
                Long nowTime = new Date().getTime();
                // Определяем, истек ли он
                if (nowTime > expTime) {
                    // уже истек
                    resultMap.clear();
                    resultMap.put("status", 2); // Истек срок действия.

                }
            }
        }else {
            resultMap.put("status", 1); // Ошибка. Неверный токен
        }
        return resultMap;

    }
}

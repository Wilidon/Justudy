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
    private final byte[] secret;

    public TokenUtils(String secret) {
        this.secret = secret.getBytes();
    }

    // Возвращаем новый токен
    public String getToken(long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id); // uid
        map.put("start", new Date().getTime()); // время генерации
        map.put("expires", new Date().getTime() + (1000 * 60 * 60 * 24)); // время истечения. 24h
        String token = null;
        try {
            token = creatToken(map);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return token;
    }


    // Генерируем токен
    public String creatToken(Map<String,Object> payloadMap) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(new JSONObject(payloadMap));
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(secret);
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }
    public Map<String,Object> checkToken(String token) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(token);
        Payload payload=jwsObject.getPayload();
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        Map<String, Object> resultMap = new HashMap<>();
        if (jwsObject.verify(jwsVerifier)) {
            resultMap.put("status", 0); // ok
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

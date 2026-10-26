package com.sap.vibertool.admin.util;

import cn.hutool.core.codec.Base64Decoder;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.model.CountryModel;
import com.sap.vibertool.admin.model.ResourceModel;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RedisUtil {
    public static StatefulRedisConnection<String, String> connect;

    public static RedisCommands<String, String> getInstance() {
        if (connect == null || !connect.isOpen()) {
            RedisURI redisUri = RedisURI.builder()
                    .withHost("43.159.36.145")
                    .withPort(6382)
                    .withPassword("redis-click-distribute")
                    .withDatabase(1)
                    .withTimeout(Duration.of(10 * 60, ChronoUnit.SECONDS))
                    .build();
            RedisClient redisClient = RedisClient.create(redisUri);
            connect = redisClient.connect();
        }
        return connect.sync();
    }

    static void main() {
//        RedisCommands<String, String> instance = getInstance();
//        instance.set("hello", "world");
//        instance.expire("hello", 60);

        /*String b="NGFhNmRmNmZkNmFiNGRjYzo0OjE3OTI4MDY0ODAwMjc6QldfT3FMWWpRMzBkNHBFZTNLUnJDSVNka090cS1ZYzRhdXhiejBHRTQyNA";
        String s = Base64Decoder.decodeStr(b);
        System.out.println(s);
        String s1 = s.split(":")[2];
        System.out.println(s1);
        System.out.println(new Date(Long.parseLong(s1)));
        long ms =Long.parseLong(s1) - System.currentTimeMillis() ;
        System.out.println(ms);
        System.out.println(ms/1000+"s");
        System.out.println(ms/1000/60+"m");
        System.out.println(ms/1000/60/60+"h");*/
    }

    static String tokenKey = "v-token";

    public static void delToken() {
        RedisCommands<String, String> instance = getInstance();
        instance.del(tokenKey);
    }

    public static String getToken() {
        RedisCommands<String, String> instance = getInstance();
        return instance.get(tokenKey);
    }

    public static void setToken(String token) {
        RedisCommands<String, String> instance = getInstance();
        instance.set(tokenKey, token);
        String s = Base64Decoder.decodeStr(token);
        long second = (Long.parseLong(s.split(":")[2]) - System.currentTimeMillis()) / 1000 - 10;
        instance.expire(tokenKey, second);
    }

    public static List<CountryModel> getCountrys() {
        String s = RedisUtil.getInstance().get("v-country");
        return JSONArray.parseArray(s, CountryModel.class);
    }

    public static void setCountrys(List<CountryModel> list) {
        String jsonString = JSON.toJSONString(list);
        RedisUtil.getInstance().set("v-country", jsonString);
    }

    public static void setResouces(String jsonString) {
        getInstance().set("v-resources", jsonString);
    }

    public static List<ResourceModel> getResouces() {
        String s = RedisUtil.getInstance().get("v-resources");
        return JSONArray.parseArray(s, ResourceModel.class);
    }
}

package com.sap.vibertool.sms.admin.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

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
}

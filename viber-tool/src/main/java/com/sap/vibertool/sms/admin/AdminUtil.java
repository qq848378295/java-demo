package com.sap.vibertool.sms.admin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.sms.admin.util.RedisUtil;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.Set;

public class AdminUtil {

    public static Set<String> stopMsgSet = Set.of("WRONG_MAX_PRICE", "不支持国家/地区", " 最高单价低于当前最低价");
    public static String authorization = "Bearer NGFhNmRmNmZkNmFiNGRjYzo0OjE3OTI2NzMyMDIxMjg6eGwwUkgwSHBLN2doVmlSVlVJM3dCcVo3R19Jb1k5Mzcyd1ZHbUIwam8zMA";

    static String USERNAME = "dots";
    static String PASSWORD = "jp4jB67ripmtQut";

    public static boolean pause(String taskId) {
        String u = "https://www.viber.mom/api/register-tasks/" + taskId + "/pause";
        String body = requestPost(u).execute().body();
//        System.out.println("pause " + taskId + " " + body);
        return isSuccess(body);
    }


    public static boolean stop(String taskId) {
        String u = "https://www.viber.mom/api/register-tasks/" + taskId + "/stop";
        String body = requestPost(u).execute().body();
//        System.out.println("stop " + taskId + " " + body);
        return isSuccess(body);
    }


    public static boolean isSuccess(String body) {
        JSONObject result = JSON.parseObject(body);
        return result.containsKey("code") && 0 == result.getInteger("code");
    }

    public static String getAuthorization() {
        authorization = authorization.replace("\n", "");
        if (!authorization.startsWith("Bearer ")) {
            authorization = "Bearer " + authorization;
        }
        return authorization;
    }

    public static HttpRequest requestPost(String url) {
        return HttpUtil.createPost(url).header("authorization", getAuthorization());
    }

    public static JSONObject requestPost(String url, JSONObject body) {
        return JSON.parseObject(HttpUtil.createPost(url).header("authorization", getAuthorization()).body(body.toJSONString()).execute().body());
    }

    public static String ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 18_5 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.5 Mobile/15E148 Safari/604.1";

    public static HttpRequest requestGet(String url) {
        return HttpUtil.createGet(url)
                .header("user-agent", ua)
                .header("authorization", getAuthorization());
    }

    public static JSONObject get(String url) {
        return JSON.parseObject(HttpUtil.createGet(url)
                .header("user-agent", ua)
                .header("authorization", getAuthorization()).execute().body());
    }

    public static boolean needStop(String lastError) {
        for (String s : stopMsgSet) {
            if (lastError != null && lastError.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static String getStopMsg(String lastError) {
        for (String s : stopMsgSet) {
            if (lastError != null && lastError.contains(s)) {
                return s;
            }
        }
        return null;
    }

    /**
     * {
     * "code": 0,
     * "msg": "success",
     * "success": true,
     * "timeStamp": 1792722323977,
     * "data": {
     * "token": "NGFhNmRmNmZkNmFiNGRjYzo0OjE3OTI4MDg3MjM5NzU6c29QNUI5T3dScGFKZlY1OFNuQ19JeDJ0N3VvazdrRzYzTU1SVm9GNjU2OA",
     * "user": {
     * "id": "4aa6df6fd6ab4dcc",
     * "username": "dots",
     * "role": "USER",
     * "permissions": [
     * "DASHBOARD_ACTIVATION_FAILURE",
     * "DASHBOARD_COST",
     * "DASHBOARD_LOSS",
     * "DASHBOARD_REGISTERED",
     * "DASHBOARD_SERVER_STATUS",
     * "DASHBOARD_THREADS",
     * "FIREFOX_VIEW",
     * "REGISTER_LOGS_VIEW",
     * "REGISTER_MONITOR_VIEW",
     * "REGISTER_TASKS_MANAGE",
     * "REGISTER_TASKS_VIEW",
     * "RISK_ANALYSIS_VIEW"
     * ],
     * "exportPolicy": null
     * }
     * }
     * }
     *
     * @param username
     * @param password
     * @return
     */
    public static JSONObject login(String username, String password) {
        String u = "https://www.viber.mom/api/auth/login";
        JSONObject body = new JSONObject();
//        body.put("username", "dots");
//        body.put("password", "jp4jB67ripmtQut");
        body.put("username", username);
        body.put("password", password);
        return requestPost(u, body);
    }

    public static void checkAuthorization() {
        //1 获取 authorization
        RedisCommands<String, String> instance = RedisUtil.getInstance();
        String token = instance.get("v-token");
        if (StrUtil.isNotBlank(token)) {
            boolean valid = checkTokenValid(token);
        }

        if (StrUtil.isBlank(token)) {
            JSONObject login = login(USERNAME, PASSWORD);
            if (!login.containsKey("code") || login.getInteger("code") != 0) {
                throw new RuntimeException("登录失败" + login);
            }
            if (login.containsKey("data") && login.getJSONObject("data").containsKey("token")) {
                token = login.getJSONObject("data").getString("token");
            } else {
                throw new RuntimeException("登录失败" + login);
            }
        }
        //.getJSONObject("data").getString("token")
    }

    private static boolean checkTokenValid(String token) {
        authorization = "";
        return false;
    }
}

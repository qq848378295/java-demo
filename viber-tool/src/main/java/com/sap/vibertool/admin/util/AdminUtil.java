package com.sap.vibertool.admin.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import com.sap.vibertool.admin.model.CountryModel;
import com.sap.vibertool.admin.model.ResourceModel;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
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
        String r = HttpUtil.createGet(url)
                .header("user-agent", ua)
                .header("authorization", getAuthorization()).execute().body();
        log.info("{} \n{}", url, r);
        return JSON.parseObject(r);
    }

    public static JSONObject get(String url, String token) {
        token = token.replace("\n", "");
        if (!token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }
        return JSON.parseObject(HttpUtil.createGet(url)
                .header("user-agent", ua)
                .header("authorization", token).execute().body());
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
        String token = RedisUtil.getToken();
        System.out.println("token:" + token);
        if (StrUtil.isNotBlank(token)) {
            try {
                checkTokenValid(token);
                System.out.println("checkToken 成功");
            } catch (Exception e) {
                RedisUtil.delToken();
                token = null;
            }
        }

        if (StrUtil.isBlank(token)) {
            JSONObject login = login(USERNAME, PASSWORD);
            if (!login.containsKey("code") || login.getInteger("code") != 0) {
                throw new RuntimeException("登录失败" + login);
            }
            System.out.println("登录 成功");
            if (login.containsKey("data") && login.getJSONObject("data").containsKey("token")) {
                token = login.getJSONObject("data").getString("token");
                checkTokenValid(token);
                System.out.println("checkToken 成功");
                RedisUtil.setToken(token);
            } else {
                throw new RuntimeException("登录失败" + login);
            }
        }
    }


    private static void checkTokenValid(String token) {
        String u = "https://www.viber.mom/api/registration-config/countries";
        JSONObject jsonObject = AdminUtil.get(u, token);
        if (!jsonObject.containsKey("data")) {
            throw new RuntimeException("checkTokenValid异常:" + jsonObject);
        }
        countrys(jsonObject);
        System.out.println("更新country成功");
        AdminUtil.authorization = token;
        resources();
        System.out.println("更新resources成功");
    }

    public static void countrys(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        System.out.println(jsonArray);
        List<CountryModel> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            CountryModel countryModel = JSON.parseObject(json.toString(), CountryModel.class);
            if (countryModel.getEnabled()) {
                String code = json.getString("code");
                CountryMapping.Country model = CountryMapping.getMap().get(code);
                if (model != null) {
                    countryModel.setContinent(model.getContinent());
                    list.add(countryModel);
                } else {
                    System.err.println("找不到对应关系:" + code);
                }
            }
        }
        RedisUtil.setCountrys(list);
    }

    public static void resources() {
        String u = "https://www.viber.mom/api/registration-config/resources";
        JSONObject jsonObject = get(u);
        if (!jsonObject.containsKey("data")) {
            throw new RuntimeException("resources异常:" + jsonObject);
        }
        JSONArray array = jsonObject.getJSONArray("data");
        List<ResourceModel> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            ResourceModel resourceModel = JSON.parseObject(json.toString(), ResourceModel.class);
            if (resourceModel.getEnabled()) {
                list.add(resourceModel);
            }
        }
        RedisUtil.setResouces(JSON.toJSONString(list));
    }

    public static JSONObject registerTasks() {
        String u = "https://www.viber.mom/api/register-tasks";
        return AdminUtil.get(u);
    }
}

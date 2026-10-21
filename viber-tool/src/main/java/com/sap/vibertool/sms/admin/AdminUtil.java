package com.sap.vibertool.sms.admin;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.Set;

public class AdminUtil {


    static Set<String> stopMsgSet = Set.of("WRONG_MAX_PRICE","不支持国家/地区");
    public static String authorization = "Bearer NGFhNmRmNmZkNmFiNGRjYzo0OjE3OTI1ODU4NjgwMTE6TWtCT0JLVGhTVEFiVzE4eG9DWWNsSk90OTZsYm1rLXdZa1lCemhKSXFUVQ";

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

    public static HttpRequest requestPost(String url) {
        return HttpUtil.createPost(url).header("authorization", authorization);
    }

    public static HttpRequest requestGet(String url) {
        return HttpUtil.createGet(url).header("authorization", authorization);
    }

    public static boolean needStop(String lastError) {
        for (String s : stopMsgSet) {
            if (lastError != null && lastError.contains(s)) {
                return true;
            }
        }
        return false;
    }

}

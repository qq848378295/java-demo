package com.sap.vibertool.sms.admin;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public class Main {

    static void main() {
        String u = "https://www.viber.mom/api/register-tasks";
        HttpRequest request = AdminUtil.requestGet(u);
        String body = request.execute().body();
//        System.out.println(body);

        JSONObject json = JSON.parseObject(body);
        JSONArray jsonArray = json.getJSONArray("data");
        int c = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.containsKey("status") && "RUNNING".equalsIgnoreCase(jsonObject.getString("status"))) {
                if (AdminUtil.needStop(jsonObject.getString("lastError"))) {
                    String taskId = jsonObject.getString("id");
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t 关闭:" + jsonObject);
                    break;
                }
                System.out.println(++c + "\t" + jsonObject);
            }
        }
    }


}

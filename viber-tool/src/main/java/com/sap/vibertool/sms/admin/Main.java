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
                String taskId = jsonObject.getString("id");
                if (AdminUtil.needStop(jsonObject.getString("lastError"))) {
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t 停止:" + jsonObject);
                    continue;
                }


                // createdAt
                Long l = jsonObject.getLong("createdAt");
//                Date d = new Date(l);
                //超过十分钟，
//                "successCount":20,"failCount":263,"fetchCount":373
                Integer successCount = jsonObject.getInteger("successCount");
                Integer failCount = jsonObject.getInteger("failCount");
                Integer fetchCount = jsonObject.getInteger("fetchCount");
                if (System.currentTimeMillis() - l > 1000 * 60 * 30 && successCount + failCount + fetchCount == 0) {
                    System.err.println("可能要关闭：" + jsonObject);
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t 停止:" + jsonObject);
                    continue;
                }
                System.out.println(++c + "\t" + jsonObject);
            } else if (jsonObject.containsKey("status") && "PAUSED".equalsIgnoreCase(jsonObject.getString("status"))) {
                String taskId = jsonObject.getString("id");
                boolean stop = AdminUtil.stop(taskId);
                System.err.println(stop + "\t 停止:" + jsonObject);
            }


        }
    }


}

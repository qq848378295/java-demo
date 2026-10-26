package com.sap.vibertool.sms.admin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.util.AdminUtil;

public class Main {

    static void main() {
        AdminUtil.checkAuthorization();


        AdminUtil.authorization = "NGFhNmRmNmZkNmFiNGRjYzo0OjE3OTI4MDg3MjM5NzU6c29QNUI5T3dScGFKZlY1OFNuQ19JeDJ0N3VvazdrRzYzTU1SVm9GNjU2OA";
        String u = "https://www.viber.mom/api/register-tasks";
        HttpRequest request = AdminUtil.requestGet(u);
        String body = request.execute().body();
//        System.out.println(body);

        JSONObject json = JSON.parseObject(body);
        JSONArray jsonArray = json.getJSONArray("data");
        int c = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String lastError = jsonObject.getString("lastError");
            if (jsonObject.containsKey("status") && "RUNNING".equalsIgnoreCase(jsonObject.getString("status"))) {
                String taskId = jsonObject.getString("id");
                if (AdminUtil.needStop(lastError)) {
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t" + AdminUtil.getStopMsg(lastError) + "\t 停止:" + jsonObject);
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
                if (System.currentTimeMillis() - l > 1000 * 60 * 60 && successCount + failCount + fetchCount == 0) {
                    System.err.println("可能要关闭：" + jsonObject);
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t 超时并=0 \t" + successCount + failCount + fetchCount + AdminUtil.getStopMsg(lastError) + "\t 停止:" + jsonObject);
                    continue;
                }
                System.out.println(++c + "\t" + jsonObject);
            } else if (jsonObject.containsKey("status") && "PAUSED".equalsIgnoreCase(jsonObject.getString("status"))) {
                String msg = AdminUtil.getStopMsg(lastError);
                if (StrUtil.isNotBlank(msg)) {
                    String taskId = jsonObject.getString("id");
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t" + AdminUtil.getStopMsg(lastError) + "\t 停止:" + jsonObject);
                }
            }
        }
        System.exit(1);
    }


}

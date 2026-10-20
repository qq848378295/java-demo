package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONObject;

public class Adb {
    static void main() {
        JSONObject body = new JSONObject();
        body.put("padCode", "ACP62K5X90A5QYVO");
        body.put("enable", "true");
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/adb", body);
        System.out.println(s);

    }
}

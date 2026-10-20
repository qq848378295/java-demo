package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public class AsyncCmd {
    static void main() {
        JSONObject body = new JSONObject();
        body.put("padCodes", List.of("ACP62K5X90A5QYVO"));
        body.put("scriptContent", "curl  http://ipinfo.io/json");
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/asyncCmd", body);
        System.out.println(s);

    }
}

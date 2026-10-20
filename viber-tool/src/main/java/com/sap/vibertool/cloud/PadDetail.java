package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONObject;

public class PadDetail {
    static void main() {
//        body.put("rows",10);
//        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/padDetail", body);
//        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/userPadList", body);
        padInfo("ACP62K5X90A5QYVO");
    }

    private static void padInfo(String padCode) {
        JSONObject body = new JSONObject();
        body.put("padCode", padCode);
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/padInfo", body);
        System.out.println(s);
    }
}

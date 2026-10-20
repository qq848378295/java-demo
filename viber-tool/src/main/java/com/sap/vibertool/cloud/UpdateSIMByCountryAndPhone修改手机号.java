package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONObject;

public class UpdateSIMByCountryAndPhone修改手机号 {
    static void main() {
        updateSIMByCountryAndPhone("ACP62K5X90A5QYVO", "BR", "+5512936262741");


//        String task ="TASK-503101666485211136";
//        JSONObject body = new JSONObject();
//        body.put("taskId", task);
//        String r = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/getUpdateSIMTaskStatus", body);
//        System.out.println(r);
    }

    /**
     *
     * @param padCode     "AC32010601132",
     * @param countryCode "US",
     * @param phoneNumber "+14155552671"
     */
    private static void updateSIMByCountryAndPhone(String padCode, String countryCode, String phoneNumber) {
        JSONObject body = new JSONObject();
        body.put("padCode", padCode);
        body.put("countryCode", countryCode);
        body.put("phoneNumber", phoneNumber);
        String r = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/updateSIMByCountryAndPhone", body);
        System.out.println(r);
    }
}

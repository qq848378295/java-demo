package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public class AddPhoneRecord {
    static void main() {
        JSONObject body = new JSONObject();
        body.put("padCodes", List.of(YunJiConstant.padCode));

        JSONArray callRecords = new JSONArray();
        body.put("callRecords",callRecords);
        V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/addPhoneRecord", body);

    }
}

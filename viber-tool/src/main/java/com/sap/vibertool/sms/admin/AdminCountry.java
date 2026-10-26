package com.sap.vibertool.sms.admin;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.util.AdminUtil;

public class AdminCountry {
    static void main() {
        String u = "https://www.viber.mom/api/registration-config/countries";
        AdminUtil.authorization = "Bearer NGFhNmRmNmZkNmFiNGRjYzo0OjE3OTI4NTI1MzY3Njg6anFvN1FrcjRzZmY4SmNjRlkyeG5aeDkzaTNtcUJwc191bEtvNUg1V2dhOA\n";
        JSONObject jsonObject = AdminUtil.get(u);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        System.out.println(jsonArray);

    }
}

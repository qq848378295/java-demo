package com.sap.vibertool.sms.admin;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

public class Countrys {
    static void main() {
        String u = "https://www.viber.mom/api/registration-config/countries";
        AdminUtil.authorization = "Bearer NGFhNmRmNmZkNmFiNGRjYzo0OjE3OTI4MDY0ODAwMjc6QldfT3FMWWpRMzBkNHBFZTNLUnJDSVNka090cS1ZYzRhdXhiejBHRTQyNA";
        JSONObject jsonObject = AdminUtil.get(u);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        System.out.println(jsonArray);

    }
}

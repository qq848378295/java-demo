package com.sap.vibertool.admin.rule;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddTaskRule {

    public static Map<String, JSONObject> countryParam = new HashMap<>();

    static {
        countryParam.put("UA", new JSONObject()
                .fluentPut("mncList", "1,3,6,7")
        );
        countryParam.put("TH", new JSONObject()
                .fluentPut("mncList", "3,5")
        );
        countryParam.put("PH", new JSONObject()
                .fluentPut("mncList", "2,3")
                .fluentPut("language", "EN")
        );
        countryParam.put("MM", new JSONObject()
                .fluentPut("mncList", "9")
                .fluentPut("language", "EN")
        );
        countryParam.put("VN", new JSONObject()
                .fluentPut("mncList", "1,7,8")
                .fluentPut("language", "VI")
        );
//        countryParam.put("PH", new JSONObject()
//                .fluentPut("mncList", "2,3")
//                .fluentPut("language", "EN")
//        );
    }
}

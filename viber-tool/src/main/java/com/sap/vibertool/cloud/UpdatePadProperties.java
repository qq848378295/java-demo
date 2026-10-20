package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public class UpdatePadProperties {
    static void main() {
        JSONObject body = new JSONObject();
        body.put("padCodes", List.of("ACP62K5X90A5QYVO"));
        String phone = "551444519777";
//        body.put("phoneNumber", "+"+phone);
//        body.put("persist.sys.cloud.phonenu", phone);

        JSONArray array = new JSONArray();
//        "propertiesName":"IMEI",
//        "propertiesValue":"897654321"

        array.add(new JSONObject()
                .fluentPut("propertiesName", "imei")
                //724053130016453
                .fluentPut("propertiesValue", "724053130016444")
        );
        body.put("modemPropertiesList", array);
        body.put("modemPersistPropertiesList", array);
        System.out.println(body);
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/updatePadProperties", body);
        System.out.println(s);
    }
}

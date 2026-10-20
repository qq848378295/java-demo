package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

public class SwitchRoot {
    static void main() {
        JSONObject body = new JSONObject();
        body.put("padCodes", List.of(YunJiConstant.padCode));
        body.put("globalRoot",false);//是否开启全局root权限，默认不开启
        body.put("packageName","local.audit.viberhook");//应用包名(非全局root必传)多个包名通过,连接
        body.put("rootStatus",1);//root状态，0：关闭 1：开启
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/switchRoot", body);
        System.out.println(s);
    }
}

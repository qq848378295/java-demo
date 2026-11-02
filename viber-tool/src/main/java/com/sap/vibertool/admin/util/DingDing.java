package com.sap.vibertool.admin.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DingDing {
    public static boolean sendMsg(String msg) {
        JSONObject body = new JSONObject();
        body.put("msgtype", "text");
        body.put("text", new JSONObject().fluentPut("content", "通知 " + msg));
        String u = "https://oapi.dingtalk.com/robot/send?access_token=dba1132eccbe75826aa3672a5d09f239941462e92cdc7466ca82a177917bb5bc";
        String post = HttpUtil.post(u, body.toJSONString());
        try {
            return JSON.parseObject(post).getInteger("errcode") == 0;
        } catch (Exception e) {
            log.error("{}", post);
            return false;
        }
    }
}

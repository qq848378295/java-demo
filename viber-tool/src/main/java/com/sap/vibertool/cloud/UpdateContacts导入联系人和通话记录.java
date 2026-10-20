package com.sap.vibertool.cloud;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class UpdateContacts导入联系人和通话记录 {
    static void main() {
        List<BrazilContactGenerator.Contact> contactList = initInfo();
        updateContacts(contactList);
        addPhoneRecord(contactList);
    }

    private static void addPhoneRecord(List<BrazilContactGenerator.Contact> contactList) {


        // 定义时间范围：2025年5月1日 到 2025年5月7日
        LocalDateTime start = LocalDateTime.of(2025, 5, 11, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 19, 23, 59, 59);

        // 基于通讯录生成20条通话记录，限制在指定时间内
        List<BrazilContactGenerator.CallRecord> myCalls = BrazilContactGenerator.generateCallRecords(contactList, RandomUtil.randomInt(50, 200), start, end);

        JSONObject body = new JSONObject();
        body.put("padCodes", List.of(YunJiConstant.padCode));
        body.put("callRecords", myCalls);
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/addPhoneRecord", body);
        log.info("s:{}",s);

    }

    private static void updateContacts(List<BrazilContactGenerator.Contact> contactList) {
        JSONObject body = new JSONObject();
        body.put("padCodes", List.of(YunJiConstant.padCode));
        body.put("operateType", 1);
        body.put("info", contactList);
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/updateContacts", body);
        log.info("s:{}",s);
    }

    private static List<BrazilContactGenerator.Contact> initInfo() {
        int loop = RandomUtil.randomInt(10, 30);
        return BrazilContactGenerator.generateContacts(loop);
    }
}

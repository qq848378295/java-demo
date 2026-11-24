package com.sap.vibertool.admin.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class RedLineUtil {
    static void main() {
        AdminUtil.checkAuthorization();
        List<String> list = getIsoCodeList();
        log.info("list:{}:{}", list.size(), list);
    }

    public static List<String> getIsoCodeList() {
        JSONObject jsonObject = AdminUtil.redLine();
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Set<String> set = new HashSet<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String dockingCode = json.getString("dockingCode");
            String country = json.getString("country");
            Integer onlineCount = json.getInteger("onlineCount");
            Integer availableCount = json.getInteger("availableCount");
            String statusMessage = json.getString("statusMessage");
            String iso3 = dockingCode.split("-")[0].toUpperCase();
            String iso2 = Iso3Mapping.getMap().get(iso3.toUpperCase());
//          log.info("{}-{} {}\t{}\t{}/{} {}", iso3, iso2, dockingCode, country, onlineCount, availableCount, statusMessage);
//            if(onlineCount > 0 && availableCount >0 ){
            if ("已启用".equalsIgnoreCase(statusMessage) && availableCount > 0) {
                set.add(iso2);
            }
        }
        return new ArrayList<>(set);
    }
}

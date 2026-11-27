package com.sap.vibertool.admin.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class WorldCodeUtil {
    static void main() {
        AdminUtil.checkAuthorization();
        List<String> list = getIsoCodeList();
        log.info("list:{}:{}", list.size(), list);
        System.exit(0);
    }

    public static List<String> getIsoCodeList() {
        JSONObject jsonObject = AdminUtil.worldCode();
        System.out.println("=======================================");
        System.out.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Set<String> set = new HashSet<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String dockingCode = json.getString("dockingCode");
            String country = json.getString("country");
            Integer onlineCount = json.getInteger("onlineCount");
            Integer availableCount = json.getInteger("availableCount");
            Boolean disabled = json.getBoolean("disabled");
            String iso3 = dockingCode.split("-")[0].toUpperCase();
            String iso2 = Iso3Mapping.getMap().get(iso3.toUpperCase());
            log.info("{}-{} {}\t{}\t{}/{} {}", iso3, iso2, dockingCode, country, onlineCount, availableCount, disabled);
//            if(onlineCount > 0 && availableCount >0 ){
            Double priceUsd = json.getDouble("priceUsd");
            if (!disabled && availableCount > 0) {
                if (priceUsd >= 0.2) {
                    System.err.println("超出价格" + iso2 + "\t" + priceUsd);
                } else {
                    set.add(iso2);
                }
            }
        }
        return new ArrayList<>(set);
    }
}

package com.sap.vibertool.admin;

import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.constant.ResourceConstant;
import com.sap.vibertool.admin.constant.TaskStatus;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.rule.CountryBlackList;
import com.sap.vibertool.admin.util.AdminUtil;
import com.sap.vibertool.admin.util.CountryMapping;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class 分析国家缺少的任务 {
    @SneakyThrows
    static void main() {
        String t = """
                UA
                RO
                IT
                TH
                MM
                DE
                KZ
                BD
                DO
                PH
                EG
                GB
                ES
                VN
                TR
                JO
                EE
                MD
                PT
                HK
                BG
                LY
                BY
                EC
                AM
                BR
                IN
                AU
                TT
                CI
                LK
                TJ
                GE
                GR
                RS
                MC
                KH
                CZ
                FJ
                MN
                MY
                NG
                JP
                MG
                AO
                YE
                DZ
                IL
                SI
                LB
                CO
                """;
        Set<String> countrySet = new HashSet<>();
        for (String s : t.split("\n")) {
            if (CountryMapping.getMap().containsKey(s)) {
                System.out.println(s);
                countrySet.add(s);
            }
        }

        List<String> resourceIds = List.of(ResourceConstant.HERO_SMS);

        Set<String> countryResourceIdSet = new HashSet<>();
        resourceIds.forEach(id -> {
            countrySet.forEach(c -> {
                countryResourceIdSet.add(id + ":" + c);
            });
        });

        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
        taskList.forEach(task -> {
            if (List.of(TaskStatus.RUNNING.name(), TaskStatus.PAUSED.name()).contains(task.getStatus())) {
                String k = task.getResourceId() + ":" + task.getCountry();
                countryResourceIdSet.remove(k);
                System.out.println("删除：" + k);
            }
        });

        // 缺少的国家
        System.out.println("缺少的:");
        String proxySession = AdminUtil.maxProxySession(taskList);

        for (String s : countryResourceIdSet) {
            String[] arr = s.split(":");
            String country = arr[1];
            //判断黑名单
            String resouceId = arr[0];
            boolean add = true;

            //hero 只能判断白名单
            if (resouceId.equalsIgnoreCase(ResourceConstant.HERO_SMS)) {
                if (!CountryBlackList.heroWhiteList.contains(country)) {
                    add = false;
                }
            }

            if (CountryBlackList.resourceIdBlackMap.containsKey(country) && CountryBlackList.resourceIdBlackMap.get(country).contains(resouceId)) {
                add = false;
            }
            if (!add) {
//                System.out.println("不添加：" + country);
            } else {
                System.out.println("添加：" + country);
                JSONObject json = AdminUtil.addTask(country, proxySession, List.of(resouceId));
                log.info("添加任务" + json);
                Thread.sleep(1000 * 60);
                continue;
            }
        }
        System.exit(1);
    }
}

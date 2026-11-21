package com.sap.vibertool.admin;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.constant.ResourceConstant;
import com.sap.vibertool.admin.constant.TaskStatus;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.rule.CountryBlackList;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TaskAddByContinentHero {


    static void main() {


        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
//        // 创建任务
        String proxySession = AdminUtil.maxProxySession(taskList);

        Set<String> taskSet = new HashSet<>();
        taskList.forEach(e -> {
            if (TaskStatus.RUNNING.name().equals(e.getStatus()) || TaskStatus.PAUSED.name().equals(e.getStatus())) {
                taskSet.add(e.getCountry() + ":" + e.getResourceId());
            }
        });
        String x = "US\n" +
                   "IL\n" +
                   "DE\n" +
                   "EG\n" +
                   "MM\n" +
                   "HK\n" +
                   "PT\n" +
                   "ES\n" +
                   "BD\n" +
                   "MN\n" +
                   "MY\n" +
                   "CO\n" +
                   "BR\n" +
                   "HK\n" +
                   "PH\n" +
                   "US\n" +
                   "JP\n" +
                   "CL\n" +
                   "DE";
        Set<String> strings = new HashSet<>(Arrays.asList(x.split("\n")));

        int addTask = 0;
        for (String country : strings) {
            if (StrUtil.isBlank(country)) {
                continue;
            }
            List<String> resourceIdListOrigin = new ArrayList<>();
            resourceIdListOrigin.add(ResourceConstant.HERO_SMS);

            List<String> resourceIdList = new ArrayList<>();

            resourceIdListOrigin.forEach(id -> {
                String k = country + ":" + id;
                if (!taskSet.contains(k)) {
                    resourceIdList.add(id);
                }
            });

            // 判断是否有国家 ResourceId 黑名单那
            Map<String, List<String>> resourceIdBlackMap = CountryBlackList.resourceIdBlackMap;
            if (resourceIdBlackMap.containsKey(country)) {
                List<String> blackList = resourceIdBlackMap.get(country);
                for (String blackResouceId : blackList) {
                    resourceIdList.remove(blackResouceId);
                }
                System.out.println(country + "匹配到黑名单" + resourceIdList + "\t" + blackList);
            }

            if (resourceIdList.isEmpty()) {
                continue;
            }

            System.out.println(resourceIdList);
            JSONObject json = AdminUtil.addTask(country, proxySession, resourceIdList);
            addTask += resourceIdList.size();
            log.info(country + "创建任务:" + json + "\t休眠30s");

            //30s 提交一个国家的任务
//            AdminUtil.sleep((RandomUtil.randomInt(0, 120) + 120) * 1000);
            AdminUtil.sleep((20 + 120) * 1000);
        }
        log.info("最终创建任务:{}", addTask);
        System.exit(0);
    }
}

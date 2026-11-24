package com.sap.vibertool.admin;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.constant.ResourceConstant;
import com.sap.vibertool.admin.constant.TaskStatus;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.rule.CountryBlackList;
import com.sap.vibertool.admin.util.AdminUtil;
import com.sap.vibertool.admin.util.WorldCodeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TaskAddByContinentWorldCode {


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


        /**
         ZA zaf
         VN vnm
         US USA
         TH tha
         PT prt
         PH phi
         MY mys
         MM mmr
         JP jpn
         ID idn
         HK hkg
         GB eng
         CO col
         CD cod
         CI civ
         */
        List<String> strings = new ArrayList<>();


        String x = "ZA\n" +
                   "VN\n" +
                   "US\n" +
                   "TH\n" +
                   "PT\n" +
                   "PH\n" +
                   "MY\n" +
                   "MM\n" +
                   "JP\n" +
                   "ID\n" +
                   "HK\n" +
                   "GB\n" +
                   "CO\n" +
                   "CD\n" +
                   "CI";
        for (String s : x.split("\n")) {
            if (!strings.contains(s)) {
                strings.add(s);
            }
        }

        //获取配置的国家列表
        strings = WorldCodeUtil.getIsoCodeList();
        int addTask = 0;
        for (String country : strings) {
            if (StrUtil.isBlank(country)) {
                continue;
            }
            List<String> resourceIdListOrigin = List.of(ResourceConstant.WORLDCODE);
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
            if (Set.of("MX","ZW","RU","RW","KG","CM","CU").contains(country)) {
                continue;
            }
            JSONObject json = AdminUtil.addTask(country, proxySession, resourceIdList);
            addTask += resourceIdList.size();
            log.info(country + "创建任务:" + json + "\t休眠30s");

            //30s 提交一个国家的任务
//            AdminUtil.sleep((RandomUtil.randomInt(0, 120) + 120) * 1000);
            AdminUtil.sleep((30) * 1000 * 0);
        }
        log.info("最终创建任务:{}", addTask);
        System.exit(0);
    }
}

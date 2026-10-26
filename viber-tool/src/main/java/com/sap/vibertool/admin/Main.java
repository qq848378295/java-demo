package com.sap.vibertool.admin;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.model.CountryModel;
import com.sap.vibertool.admin.model.ResourceConstant;
import com.sap.vibertool.admin.model.ResourceModel;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import com.sap.vibertool.admin.util.RedisUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {


    private static final String ENVIRONMENT_ID = "7d588ce83d3fe424252abfac790bc8e0";

    static void main() {

        AdminUtil.checkAuthorization();


        Set<String> set = Set.of(
//                "亚洲",
//                "欧洲",
                "北美洲",
                "南美洲"
        );
//        continentCountry(set);


        //任务列表
        List<TaskModel> taskList = registerTaskList();


//        taskList.forEach(e -> {
//            System.out.println(e.getProxySession() + "\t" + e.getCountry() + "\t" + new Date(e.getCreatedAt()) + "\t" + e.getResourceProvider() + "\t" + e.getEnvironmentId());
//        });

        //关闭任务
        stopRegister(taskList);


       /* // 创建任务
        String proxySession = maxProxySession(taskList);
        String country = "";
        List<String> resourceIdList = List.of(
//                ResourceConstant.HERO_SMS,

                ResourceConstant.GRIZZLY_SMS,
                ResourceConstant.LAO_MAO,
                ResourceConstant.SMS_BOWER,
                ResourceConstant.TIGER_SMS,
                ResourceConstant.WORLDCODE
        );
        addTask(country, proxySession, resourceIdList);*/


//        List<ResourceModel>  resouces = RedisUtil.getResouces();
//        System.out.println(resouces);


    }

    private static String maxProxySession(List<TaskModel> taskList) {
        Map<String, AtomicInteger> proxySessionMap = new HashMap<>();

        String maxProxySession = null;
        int maxProxyCount = 0;
        for (TaskModel e : taskList) {
            String proxySession = e.getProxySession();
            if (!proxySessionMap.containsKey(proxySession)) {
                proxySessionMap.put(proxySession, new AtomicInteger());
            }
            int count = proxySessionMap.get(proxySession).incrementAndGet();
            if (count > maxProxyCount) {
                maxProxyCount = count;
                maxProxySession = proxySession;
            }
        }

//        System.out.println(maxProxySession);
//        System.out.println(proxySessionMap);
        return maxProxySession;
    }


    private static void continentCountry(Set<String> set) {
        List<CountryModel> countrys = RedisUtil.getCountrys();

        countrys.forEach(countryModel -> {
            if (set.contains(countryModel.getContinent())) {
                System.out.println(countryModel.getContinent() + "\t" + new Date(countryModel.getCreatedAt()) + "\t" + countryModel.getCode());
            }
        });
    }

    private static JSONObject addTask(String country, String proxySession, List<String> resourceIdList) {
        String url = "https://www.viber.mom/api/register-tasks/batch";
        //post
        //
        List<CountryModel> countrys = RedisUtil.getCountrys();
        CountryModel countryModel = countrys.stream().filter(e -> Objects.equals(e.getCode(), country)).findFirst().get();
        StringJoiner sj = new StringJoiner(",");
        countryModel.getLanguageCodes().forEach(sj::add);

        JSONObject body = new JSONObject();
        body.put("taskCount", 100);
        body.put("threadCount", 1);
        body.put("environmentId", ENVIRONMENT_ID);
        body.put("integrityEnabled", true);
        body.put("country", country);
        body.put("mncList", "");
        body.put("language", sj.toString());
        body.put("platform", "ANDROID");
        body.put("maxPriceUsd", 0);
        body.put("proxyCountry", "");
        body.put("startImmediately", true);
        body.put("proxySession", proxySession);// 获取当前任务中最多的
        body.put("resourceParams", new JSONObject());
        body.put("resourceId", resourceIdList);

        return AdminUtil.requestPost(url, body);

        /*{
            "taskCount": 100,
            "threadCount": 1,
            "environmentId": "7d588ce83d3fe424252abfac790bc8e0",
            "integrityEnabled": true,
            "country": "EE",
            "mncList": "",
            "language": "EN,ET,FI,RU",
            "platform": "ANDROID",
            "maxPriceUsd": 0,
            "proxyCountry": "",
            "startImmediately": true,
            "proxySession": "s533855",
            "resourceParams": {},
            "resourceId": [
                "81036a3e8a6d48fb",
                "675e764f21c24b13",
                "ff1ad6a6fc7d44b9"
          ]
        }
        */

    }

    private static List<TaskModel> registerTaskList() {
        JSONObject json = AdminUtil.registerTasks();
        JSONArray jsonArray = json.getJSONArray("data");
        List<TaskModel> taskModels = JSONArray.parseArray(jsonArray.toJSONString(), TaskModel.class);
        return taskModels;
    }

    private static void stopRegister(List<TaskModel> taskList) {
//        JSONObject json = AdminUtil.registerTasks();
//        JSONArray jsonArray = json.getJSONArray("data");
        int c = 0;
        for (TaskModel taskModel : taskList) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String lastError = taskModel.getLastError();
            String status = taskModel.getStatus();
            String taskId = taskModel.getId();
            if ("RUNNING".equalsIgnoreCase(status)) {
                if (AdminUtil.needStop(lastError)) {
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t" + AdminUtil.getStopMsg(lastError) + "\t 停止:" + taskModel);
                    continue;
                }

                // createdAt
                Long createdAt = taskModel.getCreatedAt();
                Integer successCount = taskModel.getSuccessCount();
                Integer failCount = taskModel.getFailCount();
                Integer fetchCount = taskModel.getFetchCount();
                //超过60分钟，
                if (System.currentTimeMillis() - createdAt > 1000 * 60 * 60 && successCount + failCount + fetchCount == 0) {
                    System.err.println("可能要关闭：" + taskModel);
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t 超时并=0 \t" + successCount + failCount + fetchCount + AdminUtil.getStopMsg(lastError) + "\t 停止:" + taskModel);
                    continue;
                }
                System.out.println(++c + "\t" + taskModel);
            } else if ("PAUSED".equalsIgnoreCase(status)) {
                String msg = AdminUtil.getStopMsg(lastError);
                if (StrUtil.isNotBlank(msg)) {
                    boolean stop = AdminUtil.stop(taskId);
                    System.err.println(stop + "\t" + AdminUtil.getStopMsg(lastError) + "\t 停止:" + taskModel);
                }
            }
        }

    }

}

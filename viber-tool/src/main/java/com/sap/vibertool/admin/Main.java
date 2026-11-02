package com.sap.vibertool.admin;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.model.CountryModel;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import com.sap.vibertool.admin.util.RedisUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {



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
//        stopRegister(taskList);
        stopAllPauseRegisterTask(taskList);

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



    private static void continentCountry(Set<String> set) {
        List<CountryModel> countrys = RedisUtil.getCountrys();

        countrys.forEach(countryModel -> {
            if (set.contains(countryModel.getContinent())) {
                System.out.println(countryModel.getContinent() + "\t" + new Date(countryModel.getCreatedAt()) + "\t" + countryModel.getCode());
            }
        });
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

    private static void stopAllPauseRegisterTask(List<TaskModel> taskList) {
        for (TaskModel taskModel : taskList) {
            String lastError = taskModel.getLastError();
            String status = taskModel.getStatus();
            String taskId = taskModel.getId();
            if ("PAUSED".equalsIgnoreCase(status)) {
                String msg = AdminUtil.getStopMsg(lastError);
                boolean stop = AdminUtil.stop(taskId);
                System.err.println(stop + "\t" + msg + "\t 停止:" + taskModel);
            }
        }
    }

}

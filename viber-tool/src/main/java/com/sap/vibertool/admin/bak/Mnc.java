package com.sap.vibertool.admin.bak;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.constant.TaskStatus;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Mnc {

    static void main() {

        AdminUtil.checkAuthorization();

       /* List<String> ids = List.of("90ce719cd39a4253");
        for (String id : ids) {
            taskDimensions(id);
        }*/
//        taskDimensions("401fe5a3844049a7");
        List<TaskModel> taskList = AdminUtil.registerTaskList();
        for (TaskModel task : taskList) {
            if (!TaskStatus.RUNNING.name().equals(task.getStatus())) {
                continue;
            }
            if (task.getCodeArrivalRate() - task.getRecentCodeArrivalRate() <= 1) {
                continue;
            }
//            task.getSuccessCount() <=10 &&
            String country = task.getCountry();
            if (StrUtil.isBlank(country) || country.length() > 2) {
                continue;
            }
            if (task.getFetchCount() > 200) {
                log.info("id  {}  {} {} 成功:{} fetch:{}", task.getId(), task.getCountry(), task.getResourceProvider(), task.getSuccessCount(), task.getFetchCount());
                List<JSONObject> list = taskDimensions(task.getId(), task.getMncList());
            }
        }
        System.exit(0);
    }

    public static List<JSONObject> taskDimensions(String id, String taskMnc) {
        String u = "https://www.viber.mom/api/risk-analysis/task-dimensions?taskId=" + id + "&minSamples=1&limit=500";
        JSONObject jsonObject = AdminUtil.get(u);
//        System.out.println(jsonObject);
        if (!jsonObject.containsKey("data")) {
            return null;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (!data.containsKey("dimensions")) {
            return null;
        }
        JSONObject dimensions = data.getJSONObject("dimensions");
        if (dimensions.containsKey("mnc")) {
            JSONArray mnc = dimensions.getJSONArray("mnc");
            System.out.println("mnc   " + mnc);
            List<JSONObject> list = new ArrayList<>();
            Map<String, Double> map = new HashMap<>();
            for (int i = 0; i < mnc.size(); i++) {
                // 半小时更新一次： 来码率低(<1%)  更新后 设置半小时缓存
                /*

                if(redis.exists(”changeTask:+taskId)){
                    continue;
                }else{
                    change  mnc language ....
                    redis.set(key,v,30,Timeunit.minute)
                }
                **/

                JSONObject mncData = mnc.getJSONObject(i);
                String key = mncData.getString("key");
                double score = getScore(mncData);
                mncData.put("score", score);
                list.add(mncData);
                if (score != 0) {
                    map.put(key, score);
                }
            }
            // 872,69,97
            log.info("{}  结果:{}", id, map);

            Map<String, Double> sortedDesc = sortDesc(map);
            System.out.println(id + " 降序: " + sortedDesc);
            System.out.println(id + " 降序: " + sortedDesc.keySet().toString().replace(" ", "") + "\t 原始：" + taskMnc);
            return list;
        }
        return null;
    }

    public static JSONObject getTaskDimensions(String id) {
        String u = "https://www.viber.mom/api/risk-analysis/task-dimensions?taskId=" + id + "&minSamples=1&limit=500";
        JSONObject jsonObject = AdminUtil.get(u);
        if (!jsonObject.containsKey("data")) {
            return null;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (!data.containsKey("dimensions")) {
            return null;
        }
        return data.getJSONObject("dimensions");
    }

    public static List<JSONObject> taskDimensions(String id) {
        String u = "https://www.viber.mom/api/risk-analysis/task-dimensions?taskId=" + id + "&minSamples=1&limit=500";
        JSONObject jsonObject = AdminUtil.get(u);
//        System.out.println(jsonObject);
        if (!jsonObject.containsKey("data")) {
            return null;
        }
        JSONObject data = jsonObject.getJSONObject("data");
        if (!data.containsKey("dimensions")) {
            return null;
        }
        JSONObject dimensions = data.getJSONObject("dimensions");
        if (dimensions.containsKey("mnc")) {
            JSONArray mnc = dimensions.getJSONArray("mnc");
            System.out.println("mnc   " + mnc);
            List<JSONObject> list = new ArrayList<>();
            Map<String, Double> map = new HashMap<>();
            for (int i = 0; i < mnc.size(); i++) {
                // 半小时更新一次： 来码率低(<1%)  更新后 设置半小时缓存
                /*

                if(redis.exists(”changeTask:+taskId)){
                    continue;
                }else{
                    change  mnc language ....
                    redis.set(key,v,30,Timeunit.minute)
                }
                **/

                JSONObject mncData = mnc.getJSONObject(i);
                String key = mncData.getString("key");
                double score = getScore(mncData);
                mncData.put("score", score);
                list.add(mncData);
                if (score != 0) {
                    map.put(key, score);
                }
            }
            // 872,69,97
            log.info("{}  结果:{}", id, map);

            Map<String, Double> sortedDesc = sortDesc(map);
            System.out.println(id + " 降序: " + sortedDesc);
            System.out.println(id + " 降序: " + sortedDesc.keySet().toString().replace(" ", ""));
            return list;
        }
        return null;
    }

    private static double getScore(JSONObject mncData) {
        Integer total = mncData.getInteger("total");
        Double codeRate = mncData.getDouble("codeRate");
        Double codeArrived = mncData.getDouble("codeArrived");
        double r = codeArrived / total * codeRate;
        return r;
    }

    private static Map<String, Double> sortDesc(Map<String, Double> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

    }
}

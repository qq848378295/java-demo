package com.sap.vibertool.admin;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import com.sap.vibertool.admin.util.RedisUtil;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class 获取成功的任务数据 {
    static void main() throws Exception {
//        https://www.viber.mom/api/risk-analysis/task-dimensions?taskId=21cf8aba008841e2&minSamples=1&limit=5000

        //一个月的数据
        for (int i = 0; i < 5 * 30 * 24 * 12; i++) {
            try {
                abc();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("休眠");
            Thread.sleep(1000 * 60 * 1);
        }
        System.exit(0);

    }

    private static void abc() {
        AdminUtil.checkAuthorization();
        JSONArray array = AdminUtil.registerTaskLogList();
        for (int i = 0; i < array.size(); i++) {
            JSONObject json = array.getJSONObject(i);
            if (!"VERIFY_SUCCESS".equalsIgnoreCase(json.getString("status"))) {
                continue;
            }
            taskLog(json);
            log.info("process:{}/{}", i, array.size());
        }
    }

    private static void taskLog(JSONObject json) {
//        https://www.viber.mom/api/register-tasks/logs/792919c664bd4f35
        String id = json.getString("id");
        String phone = json.getString("phone");
        phone = phone.replace("+", "");
        String u = "https://www.viber.mom/api/register-tasks/logs/" + id;
        JSONObject jsonObject = AdminUtil.get(u);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject initResponse = data.getJSONObject("initResponse");

        RedisCommands<String, String> instance = RedisUtil.getInstance();

        String redisKey = "task-success:" + LocalDate.now();
        String hashKey = phone + "-" + id;
        instance.hset(redisKey, hashKey, initResponse.toString());
        long year = 60L * 60 * 24 * 365;
        instance.hexpire(redisKey, year, hashKey);
    }

    /**
     *
     * @return
     */
    public static boolean next(TaskModel taskModel) {
        String redisKey = getRedisKey(taskModel.getCountry());
        RedisCommands<String, String> instance = RedisUtil.getInstance();
        String hashKey = getHashKey(taskModel);

        if (!instance.hexists(redisKey, hashKey)) {
            return true;
        }
        String detail = instance.hget(redisKey, hashKey);
        JSONObject detailJson = JSONObject.parseObject(detail);
        if (detailJson.containsKey("task")) {
            JSONObject task = detailJson.getJSONObject("task");
            if ("STOPPED".equalsIgnoreCase(task.getString("status"))) {
//                System.err.println("之前已有数据并且是stop状态 ：" + task);
                return false;
            }
        }
        return true;
    }


    private static void taskDimensions(TaskModel taskModel, String process) {
        String taskId = taskModel.getId();
        String u = "https://www.viber.mom/api/risk-analysis/task-dimensions?taskId=" + taskId + "&minSamples=1&limit=5000";
        JSONObject json = AdminUtil.get(u);
        log.info(taskId + "\t" + json + "\t" + process);

        if (json.containsKey("code") && json.getInteger("code") == 0) {
            RedisCommands<String, String> instance = RedisUtil.getInstance();
            String country = taskModel.getCountry();
            String redisKey = getRedisKey(country);
            String hashKey = getHashKey(taskModel);
            JSONObject data = json.getJSONObject("data");
            instance.hset(redisKey, hashKey, data.toString());
            long year = 60L * 60 * 24 * 365;
            instance.hexpire(redisKey, year, hashKey);
        } else {
            throw new RuntimeException("请求报错:" + json);
        }
    }

    public static String getRedisKey(String country) {
        return "task:" + country;
    }

    private static String getHashKey(TaskModel taskModel) {
        return taskModel.getResourceProvider() + "@" + taskModel.getId();
    }
}

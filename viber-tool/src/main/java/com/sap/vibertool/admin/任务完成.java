package com.sap.vibertool.admin;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.constant.TaskStatus;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import com.sap.vibertool.admin.util.DingDing;
import com.sap.vibertool.admin.util.RedisUtil;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class 任务完成 {
    @SneakyThrows
    static void main() {

        for (int i = 0; i < 30 * 24 * 12; i++) {
            try {
                xaxxx();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("休眠五分钟");
            Thread.sleep(1000 * 60 * 5);
        }
        System.exit(0);
    }

    private static void xaxxx() {
        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();

        RedisCommands<String, String> instance = RedisUtil.getInstance();
        for (TaskModel taskModel : taskList) {

            //警告
            warning(taskModel);

            if (!"COMPLETED".equalsIgnoreCase(taskModel.getStatus())) {
                continue;
            }
            String redisKey = "task_completed:" + taskModel.getId();
            if (instance.exists(redisKey) != 0) {
                continue;
            }
            String msg = StrUtil.format("任务已结束id:{} {} ", taskModel.getId(), JSON.toJSONString(taskModel));
            if (DingDing.sendMsg(msg)) {
                instance.set(redisKey, JSON.toJSONString(taskModel));
                instance.expire(redisKey, 60 * 60 * 24 * 365); // 365d
                JSONObject body = getJSONObject(taskModel);
                AdminUtil.addTask(body);
            } else {
                log.info("钉钉通知发送失败");
            }
        }
    }

    private static JSONObject getJSONObject(TaskModel taskModel) {
        JSONObject body = new JSONObject();
        body.put("taskCount", taskModel.getTaskCount());
        body.put("threadCount", taskModel.getThreadCount());
        body.put("environmentId", taskModel.getEnvironmentId());
        body.put("integrityEnabled", taskModel.getIntegrityEnabled());
        body.put("country", taskModel.getCountry());
        body.put("mncList", taskModel.getMncList());
        body.put("language", taskModel.getLanguage());

        body.put("platform", taskModel.getPlatform());
        body.put("maxPriceUsd", 0);
        body.put("proxyCountry", taskModel.getProxyCountry());
        body.put("startImmediately", true);
        body.put("proxySession", taskModel.getProxySession());// 获取当前结束任务中
        body.put("resourceParams", new JSONObject());
        body.put("resourceId", taskModel.getResourceId());
        return body;
    }

    private static void warning(TaskModel taskModel) {
        if (TaskStatus.PAUSED.name().equals(taskModel.getStatus())) {
            if (taskModel.getLastError().startsWith("自动暂停：")) {
                DingDing.sendMsg("自动暂停任务:" + taskModel.getId() + "\t" + JSON.toJSONString(taskModel));
            }
        }
    }
}

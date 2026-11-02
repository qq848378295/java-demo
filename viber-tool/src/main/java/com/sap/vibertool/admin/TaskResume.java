package com.sap.vibertool.admin;

import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TaskResume {

    static void main() {
        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
        resumeAll(taskList);
    }

    private static void resumeAll(List<TaskModel> taskList) {
        log.info("=====================================================================");
        AtomicInteger resume = new AtomicInteger(0);
        taskList.forEach(taskModel -> {
            String taskId = taskModel.getId();
            if ("PAUSED".equalsIgnoreCase(taskModel.getStatus())) {
                AdminUtil.resume(taskId);
                resume.incrementAndGet();
            }
        });
        System.out.println("开启数量:" + resume.get());
        System.out.println("结束。。。。。。。。。。。");
        System.exit(0);
    }
}

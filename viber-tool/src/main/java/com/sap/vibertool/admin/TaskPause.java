package com.sap.vibertool.admin;

import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class TaskPause {
    static void main() {
        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
//        pauseAll(taskList);

        log.info("=====================================================================");
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger pause = new AtomicInteger(0);
        taskList.forEach(taskModel -> {
//            System.out.println(taskModel);
            String taskId = taskModel.getId();
            Long createdAt = taskModel.getCreatedAt();
            Integer fetchCount = taskModel.getFetchCount();

            if ("RUNNING".equalsIgnoreCase(taskModel.getStatus())) {
                //超过60分钟，并且取号为0
                if (System.currentTimeMillis() - createdAt > 1000 * 60 * 60 && fetchCount == 0) {
                    log.info("可能要关闭：{}", taskModel);
                    boolean stop = AdminUtil.stop(taskId);
                    log.info(counter.incrementAndGet() + " \t超时并=0 \t{}\t 停止:{}", fetchCount, taskModel);
                    return;
                }

                // 没有成功，但是取号超过300的，关闭
                if (fetchCount > 300 && taskModel.getSuccessCount() == 0) {
                    log.info("{} 可能要暂停：{}/{} {}", pause.incrementAndGet(), taskModel.getSuccessCount(), fetchCount, taskModel);
                    AdminUtil.pause(taskId);
                }
            }
        });
//        AdminUtil.resume("c297179b1d9846e9");
        System.exit(0);
    }

    private static void pauseAll(List<TaskModel> taskList) {
        log.info("=====================================================================");
        AtomicInteger pause = new AtomicInteger(0);
        taskList.forEach(taskModel -> {
            String taskId = taskModel.getId();
            if ("RUNNING".equalsIgnoreCase(taskModel.getStatus())) {
                AdminUtil.pause(taskId);
                pause.incrementAndGet();
            }
        });
        System.out.println("暂停数量:" + pause.get());
        System.exit(0);
    }
}

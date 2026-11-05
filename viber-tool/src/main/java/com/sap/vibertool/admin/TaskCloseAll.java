package com.sap.vibertool.admin;

import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class TaskCloseAll {
    static void main() {
        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
        String countrys = "HK,PH";
        taskList.parallelStream().forEach(task -> {
            String status = task.getStatus();
            if (Set.of(
                    "RUNNING",
                    "PAUSED").contains(status)) {
                String country = task.getCountry();
                if (!countrys.contains(country)) {
                    Boolean f = AdminUtil.stop(task.getId());
                    log.info("关闭成功:{}", f);
//                    AdminUtil.sleep(500);
                }
            }
        });
        System.out.println("退出结束。。。。。。。。。。。。cloaseAll");
        System.exit(0);
    }

}

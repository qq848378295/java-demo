package com.sap.vibertool.admin;

import com.sap.vibertool.admin.constant.TaskStatus;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class 判断有没有重复的 {
    static void main() {
        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();

        Set<String> taskSet = new HashSet<>();
        System.out.println("============================================");
        taskList.forEach(e -> {
            if (TaskStatus.RUNNING.name().equals(e.getStatus())) {
                String v = e.getCountry() + ":" + e.getResourceId();
                if (!taskSet.contains(v)) {
                    taskSet.add(v);
                } else {
                    System.out.println("重复：" + e);
                }
            }
        });
        System.exit(0);
    }
}

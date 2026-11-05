package com.sap.vibertool.admin;

import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class 分析 {
    static void main() {

        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
        //
        AtomicInteger counter = new AtomicInteger(0);
        System.out.println("=======================================");
        for (TaskModel taskModel : taskList) {
            Integer fetchCount = taskModel.getFetchCount();
            Integer smsTriggerCount = taskModel.getSmsTriggerCount();
            Integer callTriggerCount = taskModel.getCallTriggerCount();
            String mncList = taskModel.getMncList();
            if (!List.of("RUNNING", "PAUSED").contains(taskModel.getStatus())) {
                continue;
            }
            //没有设置mnc的
//            if (smsTriggerCount + callTriggerCount > 0 && StrUtil.isBlank(mncList)) {
//                System.out.println(counter.incrementAndGet() + "\t" + taskModel.getId() + " 符合条件的" + taskModel);
//            }

            if (fetchCount == 0 && System.currentTimeMillis() - taskModel.getCreatedAt() > 1000 * 60 * 10) {
                System.out.println(counter.incrementAndGet() +"\t"+taskModel.getId() + "\t" + taskModel);
                AdminUtil.stop(taskModel.getId());
            }

        }
        System.exit(1);
    }
}

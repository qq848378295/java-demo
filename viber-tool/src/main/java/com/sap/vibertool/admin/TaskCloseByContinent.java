package com.sap.vibertool.admin;

import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

import static com.sap.vibertool.admin.util.CountryMapping.*;

@Slf4j
public class TaskCloseByContinent {
    static void main() {
        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();

        List<String> closeList = List.of(
                nanmei, beimei,
//                yazhou, dayang,
//                ouzhou, feizhou,
                "");
        taskList.parallelStream().forEach(task -> {
            String status = task.getStatus();
            if (Set.of(
                    "RUNNING",
                    "PAUSED").contains(status)) {
                String country = task.getCountry();
                Country country1 = getMap().get(country);
                if (closeList.contains(country1.getContinent())) {
                    log.info("匹配大洲关闭:{}:{}:{}", country, country1.getContinent(), task.getId());
                    Boolean f = AdminUtil.stop(task.getId());
                    log.info("关闭成功:{}", f);
//                    AdminUtil.sleep(500);
                }
            }
        });
        System.exit(0);
    }
}

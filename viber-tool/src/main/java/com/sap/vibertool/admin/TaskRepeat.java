package com.sap.vibertool.admin;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.constant.ResourceConstant;
import com.sap.vibertool.admin.constant.TaskStatus;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.rule.CountryBlackList;
import com.sap.vibertool.admin.util.AdminUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.sap.vibertool.admin.util.CountryMapping.*;

@Slf4j
public class TaskRepeat {


    static void main() {



        Map<String, List<String>> zhouCountryMap = new HashMap<>();

        zhouCountryMap.put(yazhou, List.of(
                "PH", "MM", "TH", "AM", "VN", "IN",
                "HK", "JP", "MY",
                "TR", "BD", "MN", "NP", "ID", "KZ", "YE", "PK", "IL", "LK", "AE", "LB", "JO", "GE", "KH", "TJ", "UZ"));
        zhouCountryMap.put(ouzhou, List.of(
                "GB", "PL", "FR", "ES", "EE", "GR", "SI", "BA", "UA", "DE", "NL", "CZ",
                "RO", "IT", "SE", "MC", "PT", "BY", "MD", "HR", "RS"));
        zhouCountryMap.put(feizhou, List.of("DZ", "CD", "MG", "EG", "LY", "KE", "NG", "AO", "TN"));
        zhouCountryMap.put(beimei, List.of("US", "DO", "TT"));
        zhouCountryMap.put(nanmei, List.of(
//                "CO",
                "EC"));
        zhouCountryMap.put(dayang, List.of("AU", "FJ"));

        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
//        // 创建任务
        String proxySession = AdminUtil.maxProxySession(taskList);


        Set<String> taskSet = new HashSet<>();
        taskList.forEach(e -> {
            if (TaskStatus.RUNNING.name().equals(e.getStatus()) || TaskStatus.PAUSED.name().equals(e.getStatus())) {
                String  x=e.getCountry() + ":" + e.getResourceId();
                if(taskSet.contains(x)){
                    System.out.println("重复:"+e.getCountry()+"\t"+e.getResourceProvider());
                }else{
                    taskSet.add(x);
                }
            }
        });

        System.exit(0);
    }
}

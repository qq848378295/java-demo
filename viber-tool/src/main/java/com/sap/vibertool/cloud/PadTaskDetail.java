package com.sap.vibertool.cloud;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PadTaskDetail {
    static void main() {
        Integer taskId = 1791506595;
        String s = V2ApiRequestUtils.sendPostRequest("/vcpcloud/api/padApi/padTaskDetail", new JSONObject().fluentPut("taskIds", List.of(taskId)));
        log.info(taskId + "\t" + s);
    }

}

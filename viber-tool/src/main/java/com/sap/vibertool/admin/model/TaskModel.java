package com.sap.vibertool.admin.model;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

@Data
public class TaskModel {

    private String id;
    private String country;
    private String platform;
    private Integer taskCount;
    private Integer threadCount;
    private Integer maxPriceUsd;
    private Integer averagePriceUsd;
    private Integer successCount;
    private Integer failCount;
    private Integer fetchCount;
    private String status;
    private Boolean integrityEnabled;
    private String mncList;
    private String language;
    private String proxySession;
    private String proxyCountry;
    private String environmentId;
    private String environmentName;
    //    private String environmentBaseUrl;
    private String resourceId;
    private String resourceProvider;
    private String proxyIds;
    private String proxyNames;
    private String lastError;
    private Integer codeArrivedCount;
    private Integer codeTotalCount;
    private Integer codeArrivalRate;
    private Integer recentCodeArrivedCount;
    private Integer recentCodeTotalCount;
    private Integer recentCodeArrivalRate;
    private Integer smsTriggerCount;
    private Integer callTriggerCount;
    private Long createdAt;
    private Long updatedAt;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

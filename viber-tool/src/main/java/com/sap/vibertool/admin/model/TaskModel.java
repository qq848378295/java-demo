package com.sap.vibertool.admin.model;

import com.alibaba.fastjson2.JSON;
import lombok.Data;

@Data
public class TaskModel {

    private String id;
    private String country;
    private String platform;
    /**
     * 目标
     */
    private Integer taskCount;
    /**
     * 线程数
     */
    private Integer threadCount;
    private Integer maxPriceUsd;
    /**
     * 平均单价
     */
    private Integer averagePriceUsd;
    /**
     * 成功数量
     */
    private Integer successCount;
    /**
     * 注册失败(异常、激活失败)
     */
    private Integer failCount;
    /**
     * 取号总数
     */
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
    private String resourceParams;
    private String proxyIds;
    private String proxyNames;
    private String lastError;
    private Integer codeArrivedCount;
    private Integer codeTotalCount;
    private Integer codeArrivalRate;
    private Integer recentCodeArrivedCount;
    private Integer recentCodeTotalCount;
    private Integer recentCodeArrivalRate;
    /**
     * 短信
     */
    private Integer smsTriggerCount;
    /**
     * 未接
     */
    private Integer callTriggerCount;
    private Long createdAt;
    private Long updatedAt;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

package com.sap.vibertool.admin.model;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


/**
 * {
 * "code": "AE",
 * "nameZh": "阿拉伯联合酋长国",
 * "nameEn": "UAE",
 * "languageCodes": [ "AR", "EN" ],
 * "enabled": true,
 * "createdAt": 1782662799202,
 * "updatedAt": 1782790179217
 * }
 */
@Data
@AllArgsConstructor
public class CountryModel {

    private String continent;
    private String code;
    private String nameZh;
    private String nameEn;
    private List<String> languageCodes;
    private Boolean enabled;
    private Long createdAt;
    private Long updatedAt;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

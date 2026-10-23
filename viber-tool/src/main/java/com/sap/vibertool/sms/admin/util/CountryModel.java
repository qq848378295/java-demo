package com.sap.vibertool.sms.admin.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryModel {

    private String code;
    private String name;
    private String continent;

}

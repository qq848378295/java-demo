package com.sap.vibertool.sms.laomao;

import lombok.Data;

@Data
public class LaoMaoPrice {
    private String countryId;
    private Integer count;
    private Double cost;
    //    private String retry;
    private LaoMaoCountry country;
}

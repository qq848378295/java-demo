package com.sap.vibertool.sms.hero;

import lombok.Data;

@Data
public class HeroPrice {
    private Double defaul;
    private Double retail;
    private Double min;
    private Integer total;
    private Integer physical;

    private String countryId;
    private HeroCountry country;

//"default":0.6,
//"retail":0.72,
//"min":0.72

}

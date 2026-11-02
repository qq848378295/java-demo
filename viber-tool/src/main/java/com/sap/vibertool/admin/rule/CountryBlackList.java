package com.sap.vibertool.admin.rule;

import com.sap.vibertool.admin.constant.ResourceConstant;

import java.util.*;

public class CountryBlackList {
    public static Map<String, List<String>> resourceIdBlackMap = new HashMap<>();
    public static Set<String> heroWhiteList = new HashSet<>();

    static {
        heroWhiteList.add("US");
        heroWhiteList.add("EE");
        heroWhiteList.add("PH");
        heroWhiteList.add("TH");
        heroWhiteList.add("MM");
        heroWhiteList.add("MY");


        resourceIdBlackMap.put("IL", List.of(
                ResourceConstant.GRIZZLY_SMS,
                ResourceConstant.TIGER_SMS
        ));
        resourceIdBlackMap.put("AE", List.of(
                ResourceConstant.GRIZZLY_SMS,
                ResourceConstant.LAO_MAO,
                ResourceConstant.TIGER_SMS
        ));
        resourceIdBlackMap.put("UA", List.of(ResourceConstant.GRIZZLY_SMS));
        resourceIdBlackMap.put("NP", List.of(ResourceConstant.GRIZZLY_SMS));
        resourceIdBlackMap.put("JP", List.of(ResourceConstant.GRIZZLY_SMS));
        resourceIdBlackMap.put("FR", List.of(ResourceConstant.GRIZZLY_SMS));
        resourceIdBlackMap.put("EC", List.of(ResourceConstant.GRIZZLY_SMS));
        resourceIdBlackMap.put("EG", List.of(ResourceConstant.GRIZZLY_SMS));
        resourceIdBlackMap.put("PT", List.of(
                ResourceConstant.TIGER_SMS,
                ResourceConstant.GRIZZLY_SMS
        ));
        resourceIdBlackMap.put("US", List.of(ResourceConstant.GRIZZLY_SMS));
        resourceIdBlackMap.put("TT", List.of(
                ResourceConstant.GRIZZLY_SMS,
                ResourceConstant.LAO_MAO,
                ResourceConstant.WORLDCODE,
                ResourceConstant.TIGER_SMS
        ));
        resourceIdBlackMap.put("DO", List.of(ResourceConstant.WORLDCODE));
        resourceIdBlackMap.put("TR", List.of(ResourceConstant.WORLDCODE));
        resourceIdBlackMap.put("GR", List.of(ResourceConstant.WORLDCODE));
        resourceIdBlackMap.put("EE", List.of(ResourceConstant.WORLDCODE));
        resourceIdBlackMap.put("FJ", List.of(
                ResourceConstant.LAO_MAO
        ));
        resourceIdBlackMap.put("PL", List.of(
                ResourceConstant.LAO_MAO,
                ResourceConstant.TIGER_SMS
        ));
        resourceIdBlackMap.put("TN", List.of(
                ResourceConstant.LAO_MAO,
                ResourceConstant.GRIZZLY_SMS
        ));
        resourceIdBlackMap.put("JO", List.of(ResourceConstant.TIGER_SMS));
        resourceIdBlackMap.put("LB", List.of(ResourceConstant.TIGER_SMS));
        resourceIdBlackMap.put("BY", List.of(ResourceConstant.TIGER_SMS));
        resourceIdBlackMap.put("HR", List.of(ResourceConstant.TIGER_SMS));



        resourceIdBlackMap.put("US", List.of(
                ResourceConstant.SECOND_NUMBER,
                ResourceConstant.SMS_BOWER,
                ResourceConstant.GRIZZLY_SMS,
                ResourceConstant.HERO_SMS,
                ResourceConstant.LAO_MAO,
                ResourceConstant.WORLDCODE,
                ResourceConstant.TIGER_SMS
        ));

        //tiger
        // GR SI  UA DE CZ IT RS JP MY
        //SecondNumber
        // SI RO MC PT PL EE HR  MG LY PH MM TH VN IN HK MY ID YE PK
        //GrizzlySms
        // DE RO IT MC GR MD BY TN AM
        //LaoMao
        // NL IT MC LY TN HK JP TR
        //SMSBrower
        // JP
    }
}

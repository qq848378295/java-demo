package com.sap.vibertool.admin;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.constant.ResourceConstant;
import com.sap.vibertool.admin.model.TaskModel;
import com.sap.vibertool.admin.util.AdminUtil;
import com.sap.vibertool.admin.rule.CountryBlackList;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sap.vibertool.admin.util.CountryMapping.*;

@Slf4j
public class Task {

    static void main() throws InterruptedException {


//        亚洲：菲律宾(PH) 缅甸(MM) 泰国(TH) 亚美尼亚(AM) 越南(VN) 印度(IN) 香港(HK) 日本(JP) 马来西亚(MY) 土耳其(TR) 孟加拉国(BD) 蒙古(MN) 尼泊尔(NP) 印度尼西亚(ID) 哈萨克斯坦(KZ) 也门(YE) 巴基斯坦(PK) 以色列(IL) 斯里兰卡(LK) 阿联酋(AE) 黎巴嫩(LB) 约旦(JO) 格鲁吉亚(GE) 柬埔寨(KH) 塔吉克斯坦(TJ) 乌兹别克斯坦(UZ)
//
//        欧洲：英国(GB) 波兰(PL) 法国(FR) 西班牙(ES) 爱沙尼亚(EE) 希腊(GR) 斯洛文尼亚(SI) 乌克兰(UA) 德国(DE) 荷兰(NL) 捷克共和国(CZ) 罗马尼亚(RO) 意大利(IT) 瑞典(SE) 摩纳哥(MC) 葡萄牙(PT) 白俄罗斯(BY) 摩尔多瓦(MD) 克罗地亚(HR) 塞尔维亚(RS)
//
//        非洲：阿尔及利亚(DZ) 刚果（金）(CD) 马达加斯加(MG) 埃及(EG) 利比亚(LY) 肯尼亚(KE) 尼日利亚(NG) 安哥拉(AO) 突尼斯(TN)
//
//        北美洲：美国(US) 多米尼加共和国(DO) 特立尼达和多巴哥(TT)
//
//        南美洲：哥伦比亚(CO) 厄瓜多尔(EC)
//
//        大洋洲：澳大利亚(AU) 斐济(FJ)

        Map<String, List<String>> zhouCountryMap = new HashMap<>();

        zhouCountryMap.put(yazhou, List.of("PH", "MM", "TH", "AM", "VN", "IN", "HK", "JP", "MY", "TR", "BD", "MN", "NP", "ID", "KZ", "YE", "PK", "IL", "LK", "AE", "LB", "JO", "GE", "KH", "TJ", "UZ"));
        zhouCountryMap.put(ouzhou, List.of("GB", "PL", "FR", "ES", "EE", "GR", "SI", "UA", "DE", "NL", "CZ", "RO", "IT", "SE", "MC", "PT", "BY", "MD", "HR", "RS"));
        zhouCountryMap.put(feizhou, List.of("DZ", "CD", "MG", "EG", "LY", "KE", "NG", "AO", "TN"));
        zhouCountryMap.put(beimei, List.of("US", "DO", "TT"));
        zhouCountryMap.put(nanmei, List.of("CO", "EC"));
        zhouCountryMap.put(dayang, List.of("AU", "FJ"));


        AdminUtil.checkAuthorization();
        List<TaskModel> taskList = AdminUtil.registerTaskList();
//        // 创建任务
        String proxySession = AdminUtil.maxProxySession(taskList);

        List<String> zhouList = List.of(
                nanmei, beimei,
                yazhou, dayang,
                ouzhou, feizhou,
                ""
        );

        for (String zhou : zhouList) {
            if (StrUtil.isBlank(zhou)) {
                continue;
            }
            List<String> strings = zhouCountryMap.get(zhou);
            for (String country : strings) {
                List<String> resourceIdList = new ArrayList<>();
                resourceIdList.add(ResourceConstant.GRIZZLY_SMS);
                resourceIdList.add(ResourceConstant.LAO_MAO);
                resourceIdList.add(ResourceConstant.HERO_SMS);
                resourceIdList.add(ResourceConstant.SMS_BOWER);
                resourceIdList.add(ResourceConstant.TIGER_SMS);
                resourceIdList.add(ResourceConstant.WORLDCODE);

                // 判断是否有国家 ResourceId 黑名单那
                Map<String, List<String>> resourceIdBlackMap = CountryBlackList.resourceIdBlackMap;
                if (resourceIdBlackMap.containsKey(country)) {
                    List<String> blackList = resourceIdBlackMap.get(country);
                    for (String blackResouceId : blackList) {
                        resourceIdList.remove(blackResouceId);
                    }
                    System.out.println(country + "匹配到黑名单" + resourceIdList + "\t" + blackList);
                }
                System.out.println(resourceIdList);
                JSONObject json = AdminUtil.addTask(country, proxySession, resourceIdList);
                log.info(country + "创建任务:" + json + "\t休眠1min");
                //1min 提交一个国家的任务
                Thread.sleep(60 * 1000);
            }
        }
//        String country = "PH";
//        List<String> resourceIdList = List.of(
////                ResourceConstant.HERO_SMS,
//                ResourceConstant.GRIZZLY_SMS,
//                ResourceConstant.LAO_MAO,
////                ResourceConstant.SMS_BOWER,
////                ResourceConstant.TIGER_SMS,
////                ResourceConstant.WORLDCODE
//                ""
//        );
//        resourceIdList = resourceIdList.stream().filter(StrUtil::isNotBlank).toList();
//        System.out.println(resourceIdList);
//        JSONObject json = AdminUtil.addTask(country, proxySession, resourceIdList);
//        System.out.println("创建任务:" + json);
    }
}

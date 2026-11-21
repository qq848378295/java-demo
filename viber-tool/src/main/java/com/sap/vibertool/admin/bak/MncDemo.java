package com.sap.vibertool.admin.bak;

import com.alibaba.fastjson2.JSONObject;
import com.sap.vibertool.admin.util.AdminUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MncDemo {
    static void main() {
       /* JSONArray array = JSONArray.parseArray("""
                [{
                		"key": "41",
                		"total": 616,
                		"reginit": 393,
                		"codeArrived": 56,
                		"verifySuccess": 56,
                		"reginitRate": 63.8,
                		"codeRate": 14.25,
                		"successRate": 100.0
                	},
                	{
                		"key": "64",
                		"total": 628,
                		"reginit": 183,
                		"codeArrived": 43,
                		"verifySuccess": 43,
                		"reginitRate": 29.14,
                		"codeRate": 23.5,
                		"successRate": 100.0
                	},
                	{
                		"key": "40",
                		"total": 631,
                		"reginit": 193,
                		"codeArrived": 37,
                		"verifySuccess": 33,
                		"reginitRate": 30.59,
                		"codeRate": 19.17,
                		"successRate": 89.19
                	},
                	{
                		"key": "10",
                		"total": 634,
                		"reginit": 199,
                		"codeArrived": 37,
                		"verifySuccess": 36,
                		"reginitRate": 31.39,
                		"codeRate": 18.59,
                		"successRate": 97.3
                	},
                	{
                		"key": "70",
                		"total": 657,
                		"reginit": 250,
                		"codeArrived": 37,
                		"verifySuccess": 33,
                		"reginitRate": 38.05,
                		"codeRate": 14.8,
                		"successRate": 89.19
                	}
                ]
                """);*/
        AdminUtil.checkAuthorization();
        Mnc.taskDimensions("8fa4166fde284f68");
//        List<JSONObject> list = new ArrayList<>();
//        for (int i = 0; i < array.size(); i++) {
//            list.add(array.getJSONObject(i));
//        }
//        List<String> strings = rankKeys(list);
//        System.out.println(strings);
        System.exit(0);
    }

    private static final double EPS = 1e-9;

    public static class Score {
        private final String key;
        private final double value;

        Score(String key, double value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public double getValue() {
            return value;
        }
    }

    /**
     * 最终对外：返回分数降序的 key 列表
     */
    public static List<String> rankKeys(List<JSONObject> list) {
        return rankScores(list).stream().map(Score::getKey).collect(Collectors.toList());
    }

    /**
     * 需要看分数时用这个
     */
    public static List<Score> rankScores(List<JSONObject> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        List<Score> scores = new ArrayList<>();
        for (JSONObject d : list) {
            if (d == null) {
                continue;
            }
            Integer total = d.getInteger("total");
            Double codeRate = d.getDouble("codeRate");
            Double codeArrived = d.getDouble("codeArrived");
            String key = d.getString("key");
            if (total == null || total <= 0 || codeRate == null
                || codeArrived == null || key == null) {
                continue;   // 脏数据跳过
            }

            double r = codeArrived.doubleValue() / total.doubleValue() * codeRate;
            scores.add(new Score(key, r));
        }
        if (scores.isEmpty()) {
            return Collections.emptyList();
        }

        scores.sort(Comparator.comparingDouble(Score::getValue).reversed()
                .thenComparing(Score::getKey));
        // 同分按 key 稳定排序

        double half = scores.get(0).getValue() / 2;
        List<Score> kept = new ArrayList<>();
        for (Score s : scores) {
            if (s.getValue() + EPS < half) {
                // 尾部整体舍弃
                break;
            }
            kept.add(s);
        }
        return kept;
    }

}

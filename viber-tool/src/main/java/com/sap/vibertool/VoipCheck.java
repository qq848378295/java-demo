//package com.sap.vibertool;
//
//import cn.hutool.core.io.FileUtil;
//import com.alibaba.fastjson2.JSONArray;
//import com.alibaba.fastjson2.JSONObject;
//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
//import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
///**
// * 免费版手机号线路类型识别（判断是否为 VOIP）—— 基于 Google libphonenumber（离线、无需 API key）
// * 用法:
// * java VoipCheck +8613800138000 +12025550100
// * java VoipCheck --region CN 13800138000
// * java VoipCheck --file numbers.txt
// */
//public class VoipCheck {
//    static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
//    static final PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();
//    static final PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();
//
//    static final Map<PhoneNumberType, String> TYPE_CN = new HashMap<>();
//
//    static {
//        TYPE_CN.put(PhoneNumberType.MOBILE, "手机");
//        TYPE_CN.put(PhoneNumberType.FIXED_LINE, "座机");
//        TYPE_CN.put(PhoneNumberType.FIXED_LINE_OR_MOBILE, "座机/手机");
//        TYPE_CN.put(PhoneNumberType.VOIP, "VOIP(网络电话)");
//        TYPE_CN.put(PhoneNumberType.PERSONAL_NUMBER, "个人号(PCS)");
//        TYPE_CN.put(PhoneNumberType.PAGER, "寻呼机");
//        TYPE_CN.put(PhoneNumberType.UAN, "UAN统一号");
//        TYPE_CN.put(PhoneNumberType.VOICEMAIL, "语音信箱");
//        TYPE_CN.put(PhoneNumberType.TOLL_FREE, "免费号");
//        TYPE_CN.put(PhoneNumberType.PREMIUM_RATE, "付费号");
//        TYPE_CN.put(PhoneNumberType.SHARED_COST, "分摊费");
//        TYPE_CN.put(PhoneNumberType.UNKNOWN, "未知");
//    }
//
//
//    static String viberVerdict(PhoneNumberType t) {
//        if (t == PhoneNumberType.VOIP) {
//            return "❌ 会被 Viber 拒（VOIP）";
//        }
//        if (t == PhoneNumberType.MOBILE || t == PhoneNumberType.FIXED_LINE_OR_MOBILE) {
//            return "✅ 真实移动号，大概率可通过";
//        }
//        if (t == PhoneNumberType.FIXED_LINE) {
//            return "⚠️ 座机，风险高（可能收不到验证码）";
//        }
//        if (t == PhoneNumberType.TOLL_FREE || t == PhoneNumberType.PREMIUM_RATE
//            || t == PhoneNumberType.SHARED_COST || t == PhoneNumberType.UAN
//            || t == PhoneNumberType.PAGER || t == PhoneNumberType.VOICEMAIL) {
//            return "❌ 非普通手机，基本不行";
//        }
//        return "⚠️ 类型未知，建议用付费 line-type API 再判";
//    }
//
//    static void check(String raw, String region) {
//        try {
//            PhoneNumber num = util.parse(raw, region);
//            if (!util.isValidNumber(num)) {
//                System.out.printf("%-20s %-16s %-6s %-14s %-14s %-14s %s%n",
//                        raw, "-", "-", "无效号码", "-", "-", "❌ 号码无效（格式/号段不存在）");
//                return;
//            }
//            PhoneNumberType nt = util.getNumberType(num);
//            String e164 = util.format(num, PhoneNumberUtil.PhoneNumberFormat.E164);
//            String country = util.getRegionCodeForNumber(num);
//            String carrier = carrierMapper.getNameForNumber(num, Locale.ENGLISH);
//            if (carrier == null || carrier.isEmpty()) {
//                carrier = "-";
//            }
//            String geo = geocoder.getDescriptionForNumber(num, Locale.CHINESE);
//            if (geo == null || geo.isEmpty()) {
//                geo = geocoder.getDescriptionForNumber(num, Locale.ENGLISH);
//            }
//            if (geo == null) {
//                geo = "-";
//            }
//            System.out.printf("%-20s %-16s %-6s %-14s %-14s %-14s %s%n",
//                    raw, e164, country, TYPE_CN.getOrDefault(nt, "?"), carrier, geo, viberVerdict(nt));
//        } catch (NumberParseException e) {
//            System.out.printf("%-20s %-16s %-6s %-14s %-14s %-14s %s%n",
//                    raw, "-", "-", "解析失败", "-", "-", "❌ 无法解析：" + e.getMessage());
//        }
//    }
//
//
//    public static void x(List<String> items) {
//        for (String raw : items) {
//            check(raw, "");
//        }
//    }
//
//    static void main() {
//        String x = FileUtil.readString("C:\\Users\\84837\\antigravity-code\\java-demo\\viber-tool\\src\\main\\java\\com\\sap\\vibertool\\phone.txt", Charset.defaultCharset());
//        JSONObject json = JSONObject.parse(x);
//        JSONArray jsonArray = json.getJSONArray("data");
//        List<String> items = new ArrayList<>();
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject child = jsonArray.getJSONObject(i);
//            String phone = child.getString("phone");
//            if (!phone.startsWith("+")) {
//                phone = "+" + phone;
//            }
////            System.out.println(phone);
//            items.add(phone);
//        }
////        List<String> items = List.of("+6283852711616", "+959663196128");
////        x(items);
//        x(items);
//        System.exit(1);
//    }
//
//    public static void main1212(String[] args) throws Exception {
//        String region = "CN";
//        List<String> items = new ArrayList<>();
//        for (int i = 0; i < args.length; i++) {
//            String a = args[i];
//            switch (a) {
//                case "--region" -> region = args[++i];
//                case "--file" -> {
//                    File f = new File(args[++i]);
//                    try (BufferedReader br = new BufferedReader(
//                            new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
//                        String line;
//                        while ((line = br.readLine()) != null) {
//                            line = line.trim();
//                            if (!line.isEmpty()) {
//                                items.add(line);
//                            }
//                        }
//                    }
//                }
//                case "--stdin" -> {
//                    BufferedReader br = new BufferedReader(
//                            new InputStreamReader(System.in, StandardCharsets.UTF_8));
//                    String line;
//                    while ((line = br.readLine()) != null) {
//                        line = line.trim();
//                        if (!line.isEmpty()) {
//                            items.add(line);
//                        }
//                    }
//                }
//                case null, default -> items.add(a);
//            }
//        }
//
//        if (items.isEmpty()) {
//            System.out.println("用法: java VoipCheck +8613800138000 +12025550100");
//            System.out.println("      java VoipCheck --region CN 13800138000");
//            System.out.println("      java VoipCheck --file numbers.txt");
//            return;
//        }
//
//        System.out.printf("%-20s %-16s %-6s %-14s %-14s %-14s %s%n",
//                "输入", "E.164", "国家", "类型", "运营商", "归属地", "Viber");
//        System.out.println("-".repeat(100));
//        for (String raw : items) {
//            check(raw, region);
//        }
//    }
//}

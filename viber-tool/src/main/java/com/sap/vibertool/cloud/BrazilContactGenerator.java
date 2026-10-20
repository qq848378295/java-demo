package com.sap.vibertool.cloud;

import cn.hutool.core.util.RandomUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrazilContactGenerator {

    // 巴西常用名字库
    private static final String[] BRAZIL_MALE_NAMES = {
            "José", "João", "Antônio", "Francisco", "Carlos", "Paulo", "Pedro",
            "Lucas", "Luiz", "Marcos", "Ravi", "Miguel", "Heitor"
    };

    private static final String[] BRAZIL_FEMALE_NAMES = {
            "Maria", "Ana", "Francisca", "Antônia", "Adriana", "Juliana",
            "Márcia", "Fernanda", "Patrícia", "Aline", "Helena", "Maitê", "Cecília"
    };

    // 巴西主要城市区号 (DDD)
    private static final String[] BRAZIL_AREA_CODES = {
            "11", "21", "61", "31", "81", "85", "91", "92", "65", "45"
    };

    // 巴西常用邮箱后缀
    private static final String[] BRAZIL_EMAIL_SUFFIXES = {
            "gmail.com", "outlook.com.br", "yahoo.com.br", "hotmail.com"
    };

    private static final Random random = new Random();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 联系人数据类
     */
    public static class Contact {
        private String firstName;
        private String phone;
        private String email;

        public Contact(String firstName, String phone, String email) {
            this.firstName = firstName;
            this.phone = phone;
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return "{" +
                   "\"firstName\": \"" + firstName + "\"" +
                   ", \"phone\": \"" + phone + "\"" +
                   ", \"email\": \"" + email + "\"" +
                   "}";
        }
    }

    /**
     * 通话记录数据类
     */
    public static class CallRecord {
        private String number;
        private int inputType;
        private int duration;
        private String timeString;

        public CallRecord(String number, int inputType, int duration, String timeString) {
            this.number = number;
            this.inputType = inputType;
            this.duration = duration;
            this.timeString = timeString;
        }

        public String getNumber() {
            return number;
        }

        public int getInputType() {
            return inputType;
        }

        public int getDuration() {
            return duration;
        }

        public String getTimeString() {
            return timeString;
        }

        @Override
        public String toString() {
            return "{" +
                   "\"number\": \"" + number + "\"" +
                   ", \"inputType\": " + inputType +
                   ", \"duration\": " + duration +
                   ", \"timeString\": \"" + timeString + "\"" +
                   "}";
        }
    }

    /**
     * 生成符合巴西规范的手机号
     * 格式: +55 + 2位区号 + 9位手机号(以9开头)
     */
    private static String generateBrazilianPhone() {
        String areaCode = BRAZIL_AREA_CODES[random.nextInt(BRAZIL_AREA_CODES.length)];
        StringBuilder numberSuffix = new StringBuilder("9");
        for (int i = 0; i < 8; i++) {
            numberSuffix.append(random.nextInt(10));
        }
        // 返回包含国际区号 +55 的完整号码
        return
//                "+55" +
                areaCode + numberSuffix;
    }

    /**
     * 生成巴西风格邮箱
     */
    private static String generateBrazilianEmail(String firstName) {
        String namePart = firstName.toLowerCase() + getRandomDigits(2, 4);
        String suffix = BRAZIL_EMAIL_SUFFIXES[random.nextInt(BRAZIL_EMAIL_SUFFIXES.length)];
        return namePart + "@" + suffix;
    }

    /**
     * 辅助方法：生成指定长度范围的随机数字字符串
     */
    private static String getRandomDigits(int minLen, int maxLen) {
        int len = random.nextInt(maxLen - minLen + 1) + minLen;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成指定数量的巴西人通讯录
     */
    public static List<Contact> generateContacts(int contactCount) {
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < contactCount; i++) {
            String firstName;
            if (random.nextBoolean()) {
                firstName = BRAZIL_MALE_NAMES[random.nextInt(BRAZIL_MALE_NAMES.length)];
            } else {
                firstName = BRAZIL_FEMALE_NAMES[random.nextInt(BRAZIL_FEMALE_NAMES.length)];
            }

            String phone = generateBrazilianPhone();
            String email = generateBrazilianEmail(firstName);
            contacts.add(new Contact(firstName, phone, email));
        }
        return contacts;
    }

    /**
     * 基于通讯录生成指定数量的通话记录，支持自定义时间范围
     *
     * @param contacts  联系人列表
     * @param callCount 生成的通话记录数量
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 通话记录列表
     */
    public static List<CallRecord> generateCallRecords(List<Contact> contacts, int callCount, LocalDateTime startTime, LocalDateTime endTime) {
        List<CallRecord> callRecords = new ArrayList<>();

        // 计算时间范围内的总秒数
        long totalSeconds = ChronoUnit.SECONDS.between(startTime, endTime);

        if (totalSeconds <= 0) {
            throw new IllegalArgumentException("结束时间必须晚于开始时间");
        }

        for (int i = 0; i < callCount; i++) {
            Contact contact = contacts.get(random.nextInt(contacts.size()));

            // 在[startTime, endTime]范围内随机生成一个时间点
            long randomSeconds = random.nextInt((int) totalSeconds + 1);
            LocalDateTime callTime = startTime.plusSeconds(randomSeconds);

            int inputType = random.nextInt(3) + 1; // 1 or 2 or 3
            int duration = random.nextInt(3591) + 10; // 10 to 3600 seconds
            if (inputType == 3) {
                duration = 0;
            }
            callRecords.add(new CallRecord(
                    contact.getPhone(),
                    inputType,
                    duration,
                    callTime.format(FORMATTER)
            ));
        }
        return callRecords;
    }


    // 测试主函数
    public static void main(String[] args) {
        // 生成10个巴西人通讯录
        List<Contact> myContacts = generateContacts(RandomUtil.randomInt(50, 140));
        System.out.println("生成的巴西通讯录：");
        for (Contact c : myContacts) {
            System.out.println(c);
        }

        // 定义时间范围：2025年5月1日 到 2025年5月7日
        LocalDateTime start = LocalDateTime.of(2025, 5, 11, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 10, 19, 23, 59, 59);

        // 基于通讯录生成20条通话记录，限制在指定时间内
        List<CallRecord> myCalls = generateCallRecords(myContacts, RandomUtil.randomInt(50, 200), start, end);
        System.out.println("\n生成的通话记录（2025-05-11 至 2026-10-19）：");
        for (CallRecord cr : myCalls) {
            System.out.println(cr);
        }
    }
}

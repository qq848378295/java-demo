package com.sap.vibertool.admin.constant;

public enum TaskStatus {
    PAUSED("PAUSED", "暂停"),
    STOPPED("STOPPED", "已停止"),
    RUNNING("RUNNING", "运行中"),
    COMPLETED("COMPLETED", "完成");

    private final String name;
    private final String msg;

    TaskStatus(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

}

package org.zxg.shedlockdemo.entity;
// com.example.entity.TaskLog.java

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskLog {
    private Long id;
    private String taskName;
    // SUCCESS / FAILED
    private String status;
    private String message;
    private LocalDateTime executeTime;
}
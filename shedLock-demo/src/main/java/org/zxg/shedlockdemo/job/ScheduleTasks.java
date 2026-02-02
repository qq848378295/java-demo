package org.zxg.shedlockdemo.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.LockProviderToUse;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zxg.shedlockdemo.entity.TaskLog;
import org.zxg.shedlockdemo.mapper.TaskLogMapper;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleTasks {

    private final TaskLogMapper taskLogMapper;

    // Redis 锁 - 普通任务
    @Scheduled(cron = "*/1 * * * * ?")
    @SchedulerLock(name = "redis-generateDailyReport", lockAtMostFor = "PT4M", lockAtLeastFor = "PT3S")
    @LockProviderToUse("redisLockProvider")
    public void generateDailyReport() {
        log.info("开始生成日报表... (Redis 锁)");
        try {
            Thread.sleep(2000);
            log.info("日报表生成完成，已记录日志");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("任务中断", e);
        }
    }


    // MySQL 锁 - 严格任务
    @Scheduled(cron = "*/10 * * * * ?")
    @SchedulerLock(name = "db-dailySettlement", lockAtMostFor = "PT1M", lockAtLeastFor = "PT30S")
    @LockProviderToUse("jdbcLockProvider")
    public void dailySettlement() {
        log.info("开始日终结算... (MySQL 锁 - 严格)");
        try {
            Thread.sleep(15000);
            // 记录严格日志
            TaskLog logEntry = new TaskLog();
            logEntry.setTaskName("dailySettlement");
            logEntry.setStatus("SUCCESS");
            logEntry.setMessage("日终结算完成，涉及资金数据");
            logEntry.setExecuteTime(LocalDateTime.now());
            taskLogMapper.insert(logEntry);

            log.info("日终结算完成，已记录日志");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("结算任务中断", e);
        }
    }

}
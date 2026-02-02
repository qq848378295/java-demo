package org.zxg.shedlockdemo.job;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zxg.shedlockdemo.mapper.TaskLogMapper;

import java.time.Duration;
import java.time.Instant;

/**
 * 方式1：使用 executeWithLock（最常用）
 * 注入 LockProvider，然后调用：executeWithLock
 */
@Service
@Slf4j
public class ManualTaskService {

    // 注入你需要的那个 LockProvider（这里示例用 jdbcLockProvider - 严格模式）
    @Autowired
    @Qualifier("jdbcLockProvider")
    private LockProvider lockProvider;
    // 或 private final LockProvider redisLockProvider;  // 如果想用 Redis
    @Autowired
    private TaskLogMapper taskLogMapper;

    /**
     * 通过 API 或其他地方手动触发结算任务（防分布式重复执行）
     */
    public void triggerSettlementManually() {
        // 自定义锁名，与 @SchedulerLock 的 name 不同即可，避免冲突
        String lockName = "manual_daily_settlement";

        LockConfiguration lockConfig = new LockConfiguration(
                // 当前时间
                Instant.now(),
                // 锁唯一名称
                lockName,
                // lockAtMostFor：最多持有 5 分钟（防死锁）
                Duration.ofMinutes(5),
                // lockAtLeastFor：至少持有 5 秒钟（防短任务重复）
                Duration.ofSeconds(5)
        );

        // 执行带锁的任务
        var lockOpt = lockProvider.lock(lockConfig);

        if (lockOpt.isEmpty()) {
            log.warn("锁被占用，跳过本次手动执行: {}", lockName);
            // 可以抛异常、返回 false、记录日志等
            return;
        }
        SimpleLock lock = lockOpt.get();
        try {
            // 这里是你的核心业务逻辑
            log.info("【手动触发】开始执行结算逻辑... (持有锁: {})", lockName);
            try {
                // 模拟耗时 业务
                Thread.sleep(5000);
                log.info("【手动触发】结算完成");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("中断", e);
            }
        } finally {
            log.info("【手动触发】结算完成, (释放锁: {})", lockName);
            lock.unlock();
        }
    }

}

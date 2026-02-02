package org.zxg.shedlockdemo.job;

import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class ManualTaskService2 {
    @Autowired
    @Qualifier("jdbcLockProvider")
    private  LockProvider jdbcLockProvider;

    @Autowired
    @Qualifier("redisLockProvider")
    private  LockProvider redisLockProvider;


    /**
     * 更细粒度控制（手动 lock + unlock）
     */
    public void triggerWithManualLock() {
        String lockName = "manual_critical_operation";

        LockConfiguration lockConfig = new LockConfiguration(
                Instant.now(),
                lockName,
                Duration.ofMinutes(1),   // 最长持有
                Duration.ofSeconds(1)  // 最短持有
        );

//        Optional<SimpleLock> lockOpt = jdbcLockProvider.lock(lockConfig);
        Optional<SimpleLock> lockOpt = jdbcLockProvider.lock(lockConfig);

        if (lockOpt.isEmpty()) {
            log.warn("无法获取锁，任务已在其他节点执行: {}", lockName);
            return;
        }

        // 获取到锁
        SimpleLock lock = lockOpt.get();

        try {
            // 业务逻辑...
            log.info("获取锁成功，开始执行...");

            // 可以中途延长锁（如果任务很长）
            // lock.extend(Duration.ofMinutes(10));  // 延长 lockAtMostFor

            // 你的代码...
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error("执行异常", e);
        } finally {
            log.info("释放锁成功，任务完成...");
            lock.unlock();  // 必须释放！
        }
    }

    /**
     * 更优雅的封装方式（推荐长期使用）
     */
    public void simple() {
        String lockName = "manual_critical_operation_simple";
        LockConfiguration lockConfig = new LockConfiguration(
                Instant.now(),
                lockName,
                Duration.ofMinutes(1),   // 最长持有
                Duration.ofSeconds(5)  // 最短持有
        );
        withLock(jdbcLockProvider, lockConfig, () -> {
            // 你的业务逻辑
            log.info("正在执行...");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void withLock(LockProvider provider, LockConfiguration config, Runnable action) {
        Optional<SimpleLock> opt = provider.lock(config);
        if (opt.isEmpty()) {
            log.warn("锁获取失败: {}", config.getName());
            return;
        }

        SimpleLock lock = opt.get();
        try {
            action.run();
        } finally {
            lock.unlock();
            log.info("锁释放完成: {}", config.getName());
        }
    }

}

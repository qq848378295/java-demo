CREATE TABLE shedlock_critical (  -- 或 shedlock，如果你没自定义表名
               name        VARCHAR(64)  NOT NULL,          -- 锁的名称，通常就是 @SchedulerLock 的 name 值（如 "dailySettlement"）
               lock_until  TIMESTAMP(3) NOT NULL,          -- 锁的过期时间（lockAtMostFor 决定的）
               locked_at   TIMESTAMP(3) NOT NULL,          -- 锁被获取的时间
               locked_by   VARCHAR(255) NOT NULL,          -- 哪个实例/节点/线程持有锁（通常是 hostname + 线程信息）
               PRIMARY KEY (name)
);

-- 任务执行记录只出现一次（防重成功）。
CREATE TABLE t_task_log (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            task_name VARCHAR(100) NOT NULL,
                            status VARCHAR(20) NOT NULL,
                            message TEXT,
                            execute_time DATETIME NOT NULL
);
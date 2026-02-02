package org.zxg.shedlockdemo.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30M")
public class ShedLockConfig {

    @Bean("redisLockProvider")
    public LockProvider redisLockProvider(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockProvider(redisConnectionFactory);
    }

    @Bean("jdbcLockProvider")
    public LockProvider jdbcLockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
                        // 严格任务专用表
                        .withTableName("shedlock_critical")
                        // 使用 DB 时间戳，推荐
                        .usingDbTime()
                        .build()
        );
    }
}
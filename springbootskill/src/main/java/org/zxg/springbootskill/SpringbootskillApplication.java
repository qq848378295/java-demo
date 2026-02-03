package org.zxg.springbootskill;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.zxg.springbootskill.properties.AppProperties;

import java.time.LocalDateTime;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class SpringbootskillApplication {
    @Resource
    private AppProperties appProperties;

    @Resource
    private Job job;

    @PostConstruct
    public void init() {
        log.info("==================appProperties: {}", appProperties);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootskillApplication.class, args);
    }

    @Scheduled(cron = "*/3 * * * * ?")
    public void reportCurrentTime() {
        log.info("Current time: {}", LocalDateTime.now());
    }

}

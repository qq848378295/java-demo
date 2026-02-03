package org.zxg.shedlockdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@Slf4j
@EnableScheduling
@SpringBootApplication
public class ShedLockDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShedLockDemoApplication.class, args);
    }

    @Scheduled(cron = "* * * * * *")
    public void aaa(){
        log.info("aaaaaaaaaaaaaaaaa");
    }
}

package org.zxg.springbootskill;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "job.enable", havingValue = "true")
public class Job {

    @PostConstruct
    public void init() {
        log.info("Job init=========================");
    }


}

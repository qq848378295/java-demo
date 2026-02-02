package org.zxg.shedlockdemo.api;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxg.shedlockdemo.job.ManualTaskService;
import org.zxg.shedlockdemo.job.ManualTaskService2;

@RestController
public class Api {

    @Resource
    private ManualTaskService2 manualTaskService2;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @Resource
    private ManualTaskService manualTaskService;

    @RequestMapping("/test")
    public String test() {
        Thread.ofVirtual().start(() -> {
            manualTaskService.triggerSettlementManually();
        });
        Thread.ofVirtual().start(() -> {
            manualTaskService.triggerSettlementManually();
        });
        return "test";
    }


    @RequestMapping("/test2")
    public String test2() {
        Thread.ofVirtual().start(() -> {
            manualTaskService2.triggerWithManualLock();
        });
        Thread.ofVirtual().start(() -> {
            manualTaskService2.triggerWithManualLock();
        });
        return "test2";
    }

    @RequestMapping("/simple")
    public String simple() {
        Thread.ofVirtual().start(() -> {
            manualTaskService2.simple();
        });
        Thread.ofVirtual().start(() -> {
            manualTaskService2.simple();
        });
        return "simple";
    }


}

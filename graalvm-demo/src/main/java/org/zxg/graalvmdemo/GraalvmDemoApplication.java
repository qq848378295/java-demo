package org.zxg.graalvmdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author 84837
 */
@RestController
@SpringBootApplication
public class GraalvmDemoApplication {

    @RequestMapping("/")
    String home() {
        String s= "1-2-";
        return s+"=Hello World!" + LocalDateTime.now();
    }

    public static void main(String[] args) {
        SpringApplication.run(GraalvmDemoApplication.class, args);
    }

}

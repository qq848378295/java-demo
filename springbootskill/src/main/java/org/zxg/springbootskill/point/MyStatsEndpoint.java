package org.zxg.springbootskill.point;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义端点
 */
@Component
@Endpoint(id = "mystats")
public class MyStatsEndpoint {

    @ReadOperation
    public Map<String, Object> showStats() {
        return Map.of("coffeeLeft", 0, "bugsFixed", 42);
    }
}
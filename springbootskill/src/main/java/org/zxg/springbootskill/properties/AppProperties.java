package org.zxg.springbootskill.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private int timeout;
    private String name;
}

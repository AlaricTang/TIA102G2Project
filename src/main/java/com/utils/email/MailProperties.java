package com.utils.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
@Data
public class MailProperties {
    private String sender;
    private String password; // 建議用環境變數注入
    private String host;
    private int port;
    private boolean ssl;
}

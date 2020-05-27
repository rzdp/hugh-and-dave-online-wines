package com.rzdp.winestoreapi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ssh")
@Data
public class SshProperties {

    private String host;
    private int port;
    private String username;
    private String password;
}

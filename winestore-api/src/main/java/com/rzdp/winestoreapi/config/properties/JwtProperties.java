package com.rzdp.winestoreapi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth.jwt")
@Data
public class JwtProperties {

    private String secret;
    private int expirationInMillis;

}

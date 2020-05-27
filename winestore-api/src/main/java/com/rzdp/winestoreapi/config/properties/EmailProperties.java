package com.rzdp.winestoreapi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
@Data
public class EmailProperties {

    private String sender;
    private RegistrationVerification registrationVerification;

    @Data
    public static class RegistrationVerification {
        private String subject;
        private String url;
    }
}

package com.rzdp.winestoreapi.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message")
@PropertySource("classpath:messages/messages.properties")
@Data
public class MessageProperties {

    private SuccessMessage success;

    @Data
    public static class SuccessMessage {
        private String register;
        private String verifyUser;
    }
}

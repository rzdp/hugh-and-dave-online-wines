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
    private ExceptionMessage exception;

    @Data
    public static class SuccessMessage {
        private String register;
        private String verifyUser;
        private String updatePhoto;
    }

    @Data
    public static class ExceptionMessage {
        private AccountMessage account;
        private UserMessage user;
        private RoleMessage role;
        private SshMessage ssh;

        @Data
        public static class AccountMessage {
            private String alreadyExist;
            private String alreadyVerified;
            private String dataNotFound;
        }

        @Data
        public static class UserMessage {
            private String updatePhoto;
            private String dataNotFound;
        }

        @Data
        public static class RoleMessage {
            private String dataNotFound;
        }

        @Data
        public static class SshMessage {
            private String uploadFile;
        }

    }
}

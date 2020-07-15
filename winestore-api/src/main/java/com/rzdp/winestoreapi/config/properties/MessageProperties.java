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
        private String notifySignUp;
        private String verifySignUp;
        private String updatePhoto;
    }

    @Data
    public static class ExceptionMessage {
        private DataNotFound dataNotFound;
        private AlreadyExist alreadyExist;
        private AlreadyVerified alreadyVerified;
        private UniqueData uniqueData;
        private UpdatePhoto updatePhoto;
        private UploadFile uploadFile;
        private UserVerification userVerification;
        private ConfirmSignUp confirmSignUp;


        @Data
        public static class DataNotFound {
            private String account;
            private String role;
            private String user;
        }

        @Data
        public static class AlreadyExist {
            private String account;
        }

        @Data
        public static class AlreadyVerified {
            private String account;
        }

        @Data
        public static class UniqueData {
            private String userCode;
        }

        @Data
        public static class UpdatePhoto {
            private String user;
        }

        @Data
        public static class UploadFile {
            private String ssh;
        }

        @Data
        public static class UserVerification {
            private String email;
        }

        @Data
        public static class ConfirmSignUp {
            private String invalidCode;
        }
    }
}

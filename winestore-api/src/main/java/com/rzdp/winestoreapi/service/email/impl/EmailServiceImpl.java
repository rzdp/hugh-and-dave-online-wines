package com.rzdp.winestoreapi.service.email.impl;

import com.rzdp.winestoreapi.config.properties.EmailProperties;
import com.rzdp.winestoreapi.config.properties.MessageProperties;
import com.rzdp.winestoreapi.dto.MailDto;
import com.rzdp.winestoreapi.entity.User;
import com.rzdp.winestoreapi.exception.EmailException;
import com.rzdp.winestoreapi.service.email.EmailService;
import com.rzdp.winestoreapi.utils.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String FREEMARKER_USER_VERIFICATION = "user-verification.ftl";
    private static final String CONTENT_ID_LOGO = "logo";
    private static final String RESOURCE_LOGO = "images/logo-1000x1000.png";


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CreateUserCode createUserCode;
    private final JavaMailSender javaMailSender;
    private final Configuration freeMarkerConfiguration;
    private final EmailProperties emailProperties;
    private final MessageProperties messageProperties;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            Configuration freeMarkerConfiguration,
                            EmailProperties emailProperties,
                            MessageProperties messageProperties) {
        this.javaMailSender = javaMailSender;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
        this.emailProperties = emailProperties;
        this.messageProperties = messageProperties;
    }

    @Override
    public void sendUserVerificationEmail(User user, String email) {
        log.info("Sending user verification via email to user");
        long userId = user.getUserId();

        Map<String, Object> props = new HashMap<>();
        props.put("firstName", user.getFirstName());
        props.put("code", StringUtils.generateOtp(6));

        MailDto mailDto = MailDto.builder()
                .sender(emailProperties.getSender())
                .receiver(email)
                .subject(emailProperties.getRegistrationVerification().getSubject())
                .props(props)
                .build();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            Template template = freeMarkerConfiguration.getTemplate(FREEMARKER_USER_VERIFICATION);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,
                    mailDto.getProps());
            helper.setFrom(new InternetAddress(mailDto.getSender()));
            helper.setTo(mailDto.getReceiver());
            helper.setSubject(mailDto.getSubject());
            helper.setText(html, true);
            helper.addInline(CONTENT_ID_LOGO, new ClassPathResource(RESOURCE_LOGO));
            javaMailSender.send(message);
            log.info("User verification email sent!");
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("ERROR: Unable to send email notification: {}", e);
            throw new EmailException(messageProperties.getException()
                    .getUserVerification().getEmail());
        }
    }
}


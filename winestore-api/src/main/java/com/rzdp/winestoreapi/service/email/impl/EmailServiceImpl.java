package com.rzdp.winestoreapi.service.email.impl;

import com.rzdp.winestoreapi.dto.MailDto;
import com.rzdp.winestoreapi.service.email.EmailService;
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

@Service
public class EmailServiceImpl implements EmailService {

    private static final String FREEMARKER_USER_VERIFICATION = "user-verification.ftl";
    private static final String CONTENT_ID_LOGO = "logo";
    private static final String RESOURCE_LOGO = "images/logo-1000x1000.png";


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JavaMailSender javaMailSender;
    private final Configuration freeMarkerConfiguration;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            Configuration freeMarkerConfiguration) {
        this.javaMailSender = javaMailSender;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    @Override
    public boolean sendUserVerificationEmail(MailDto mailDto) {
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
        } catch (MessagingException | IOException | TemplateException e) {
            log.error("ERROR: Unable to send email notification: {}", e);
        }
        return false;
    }
}


package com.tts.cp.lib.email.service.impl.helper;

import com.tts.cp.lib.email.bean.EmailEntity;
import com.tts.cp.lib.email.service.MailHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
public abstract class AbstractMailHelper implements MailHelper {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String sender;

    public void generalSendEmail(EmailEntity emailEntity) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        File[] attachments = emailEntity.getAttachments();
        boolean multipart = null != attachments && attachments.length > 0;
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, multipart); //这个是java发邮件的工具类MimeMessageHelper

        helper.setFrom(sender);
        helper.setTo(emailEntity.getSendTo().split(","));
        helper.setSubject(emailEntity.getEmailSubject());
        helper.setText(emailEntity.getEmailContent(), true);

        String bcc = emailEntity.getBcc();
        if (StringUtils.hasText(bcc)) {
            helper.setBcc(emailEntity.getBcc().split(","));
        }

        if (multipart) {
            for (File attach : attachments) {
                helper.addAttachment(attach.getName(), attach);
            }
        }

        log.info("Start sending email -- " + emailEntity.getEmailSubject());
        mailSender.send(mimeMessage);
    }
}

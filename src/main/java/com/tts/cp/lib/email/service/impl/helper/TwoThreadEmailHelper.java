package com.tts.cp.lib.email.service.impl.helper;

import com.tts.cp.lib.email.bean.EmailEntity;
import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Alley zhao created on 2021/10/18.
 */
@Slf4j
@Service("TwoThreadEmailHelper")
public class TwoThreadEmailHelper extends AbstractMailHelper {

    @Async // 多线程注解，主线程遇到此注解自动返回
    @Override
    public StandardResponse sendEmail(Map<String, Object> emailParams) throws Exception {
        log.info("------TwoThreadEmailHelper");
        EmailEntity emailEntity=new EmailEntity();
        emailEntity.setEmailSubject("Email 主题");
        emailEntity.setEmailContent("Email Content");
        emailEntity.setSendTo("alley.zhao@easternphoenix.com");
        this.generalSendEmail(emailEntity);
        log.info("------TwoThreadEmailHelper  Success");
        return null;
    }
}

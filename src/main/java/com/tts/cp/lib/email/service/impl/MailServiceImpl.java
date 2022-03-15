package com.tts.cp.lib.email.service.impl;

import com.tts.cp.lib.email.service.MailHelper;
import com.tts.cp.lib.email.service.MailService;
import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private BeanFactory beans;

    @Override
    public StandardResponse sendEmail(Map<String, Object> emailParams) {
        log.info("-------日志打印");
        String emailKey = (String) emailParams.get("emailKey");
        StandardResponse standardResponse = new StandardResponse();
        if (!StringUtils.hasText(emailKey)) { //String.hasText(String) 空是true
            standardResponse.setSuccess(true);
            standardResponse.setStatusCode("MISS EMAIL CODE");
            return standardResponse;
        }

        try { // BeanFactory 根据map的value来确定到哪个service
            return beans.getBean(emailKey, MailHelper.class).sendEmail(emailParams);
        } catch (Exception e) {
            log.error(e.toString());
            standardResponse.setStatusDesc(e.getMessage());
            return standardResponse;
        }
    }

}

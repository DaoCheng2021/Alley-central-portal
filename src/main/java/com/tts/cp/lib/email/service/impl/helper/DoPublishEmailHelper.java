package com.tts.cp.lib.email.service.impl.helper;

import com.tts.cp.lib.email.bean.EmailEntity;
import com.tts.cp.lib.email.bean.PubRequest;
import com.tts.lib.web.StandardResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alley zhao created on 2022/3/4.
 */
@Slf4j
@Service(value = "DoPublishEmailHelper")
public class DoPublishEmailHelper extends AbstractMailHelper {

    @Value(value = "${icon.email.mail-do-publish.emailMode}")
    private String emailMode;

    @Value(value = "${icon.email.mail-do-publish.subject}")
    private String subject;

    @Value(value = "${icon.email.mail-do-publish.template}")
    private String emailTpl;

    @Value(value = "${icon.email.mail-do-publish.sendTo}")
    private String sendTo;

    @Autowired
    @Qualifier(value = "getFreeMarkerConfiguration")
    private Configuration freemarkerConfig;

    @Override
    public StandardResponse sendEmail(Map<String, Object> emailParams) {
        log.info("--DoPublishEmailHelper:{}", emailParams.size());
        StandardResponse sr = new StandardResponse();
        try {
            EmailEntity emailEntity = new EmailEntity();
            emailParams.put("emailMode", emailMode);
            if (null == emailParams.get("requestData")) {
                // send subject
                emailEntity.setEmailSubject(String.format(subject, "不是html主题", "不是html主题", "不是html主题"));
                emailEntity.setEmailContent("这是 Send Email Content");
            } else {
                PubRequest pubRequest = (PubRequest) emailParams.get("requestData");
                Set<String> publishTos = StringUtils.commaDelimitedListToSet(pubRequest.getPublishTo());
                Set publishToSet = new HashSet();
                for (String publishTo : publishTos) {
                    publishToSet.add(publishTo + " Assessment Tool");
                }
                emailParams.put("tools", publishToSet); // 传入set集合进去，报表自动循环遍历数据
                Template template = freemarkerConfig.getTemplate(emailTpl);
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, emailParams);
                emailEntity.setEmailSubject(String.format(subject, "Html主题", "Html主题", "Html主题"));
                emailEntity.setEmailContent(html);
            }
            emailEntity.setSendTo(sendTo);

            this.generalSendEmail(emailEntity);

            sr.setStatusDesc("Success");
            sr.setStatusCode("100");
            sr.setSuccess(true);
            return sr;
        } catch (Exception e) {
            e.printStackTrace();
            sr.setSuccess(false);
            sr.setStatusDesc(e.getMessage());
            return sr;
        }
    }
}

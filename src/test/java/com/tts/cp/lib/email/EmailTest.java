package com.tts.cp.lib.email;

import com.tts.cp.lib.email.bean.PubRequest;
import com.tts.cp.lib.email.service.MailService;
import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/10/18.
 */
//发邮件的测试类
@Slf4j
@SpringBootTest
public class EmailTest {

    @Autowired
    private MailService mailService;

    @Test //发邮件的测试类
    public void TestTimedTaskWeekly() {
        Map<String, Object> map = new HashMap<>();
        map.put("emailKey", "PendingIconEmailHelper"); // value是service的名字
        StandardResponse sr = mailService.sendEmail(map);
    }

    /*
     * 这个service用了@Async多线程注解，Junit测试多线程，主线程结束之后就全部结束了，不会等子线程运行完毕。
     * 所以junit测试多线程加一个主线程睡眠时间
     * */
    @Test
    public void TestTimedTaskMinute() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("emailKey", "TwoThreadEmailHelper");
        mailService.sendEmail(map);
        log.info("主线程运行完毕");
        Thread.sleep(30000);
    }

    @Test
    public void TestDoPublishEmailHelper() throws InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("emailKey", "DoPublishEmailHelper");
        PubRequest request = new PubRequest();
        request.setPublishTo("ACE,DEV");
        request.setPubRequestId("requestId");
        request.setRequestedBy("requestedBy");

        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-ss HH:mm:ss");
        Date date = new Date();
        String format = sdf.format(date);
//        request.setRequestDate();
        request.setStatus(new Date().toString());
        request.setBmus("bmu");
        request.setLanguageNames("languageNames");
        ConfPerform confPerform = new ConfPerform();
        confPerform.setPerformTypeDesc("performTypeDesc");
        confPerform.setTemplateId("templateId");
        map.put("confPerform", confPerform);
        map.put("requestData", request);
        mailService.sendEmail(map);
        log.info("主线程运行完毕");
//        Thread.sleep(30000);
    }
    @Test
    public void test01(){
        System.out.println(new Date());
    }

}

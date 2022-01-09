package com.tts.cp.lib.email;

import com.tts.cp.lib.email.service.MailService;
import com.tts.lib.web.StandardResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/10/18.
 */
//发邮件的测试类
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

    @Test
    public void TestTimedTaskMinute() {
        Map<String, Object> map = new HashMap<>();
        map.put("emailKey", "TwoThreadEmailHelper");
        mailService.sendEmail(map);
    }
}

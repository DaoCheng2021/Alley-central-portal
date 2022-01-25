package com.tts.cp.lib.visit.controller;

import com.tts.cp.lib.common.Product;
import com.tts.cp.lib.common.RedisUtil;
import com.tts.cp.lib.email.service.MailService;
import com.tts.cp.lib.visit.bean.User;
import com.tts.cp.lib.visit.service.MultiThreadingService;
import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/11/26.
 */
@Slf4j
@RestController
@RequestMapping("redisController")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MultiThreadingService multiThreadingService;

    @Autowired
    private MailService mailService;

    @GetMapping("/setRedis")
    private String setRedis(@RequestParam Map<String, Object> paramMap) {
        String nameKey = paramMap.get("nameKey").toString();
        String nameValue = paramMap.get("nameValue").toString();
        redisUtil.set(nameKey, nameValue);
        return "加入成功";
    }

    @GetMapping("/getName")
    private Map<String, Object> getName(@RequestParam Map<String, Object> paramsMap) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("name", redisUtil.get("name").toString());
        return resultMap;
    }

    @GetMapping("/threan")
    private String getThrean() {
        log.info("主线程");
        multiThreadingService.testMultiThreading();
        log.info("主线程完毕");
        return "完成";
    }

    //前端调用接口，多线程发邮件。主线程先返回，子线程发邮件  @Async注解
    //  http://localhost:8090/redisController/sendEmail?emailKey=TwoThreadEmailHelper
    @GetMapping("/sendEmail")
    private String sendEmail(@RequestParam Map<String, Object> paramMap) {
        log.info("sendEmail");
        Map<String, Object> resultMap = new HashMap<>();
        mailService.sendEmail(paramMap);
        log.info("sendEmail End");
        return "发送成功";
    }

    /*
    * @RequestBody注解，如果前端传过来的实体类是JSON类型的，必须要加此注解，如果是params类型的可以不用
    * 一个接口里面@RequestBody只能有一个，@RequestParam 可以有多个。
    * */
    @RequestMapping("/getUser2")
    private String getUser2(@RequestBody User user, @RequestParam(value = "listName") List<String> listName) {
        System.out.println(user.toString());
        return user.toString() + "-listName:" + listName.toString();
    }

    @RequestMapping("/getUser3")
    private String getUser3(@RequestBody Product product) {
        System.out.println(product);
        return product.toString();
    }

}

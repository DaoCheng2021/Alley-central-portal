package com.tts.cp.lib.visit.controller;

import com.tts.cp.lib.common.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
        resultMap.put("name",redisUtil.get("name").toString());
        return resultMap;
    }

}

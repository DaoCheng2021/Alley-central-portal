package com.tts.cp.lib.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.common.ConfigurationData;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/9/6.
 */
@SpringBootTest
public class VisitServiceTest {

    @Autowired
    private ConfigurationData configurationData;

    @Value("${template2.defaultId}") // 获取一个配置文件的String的值
    private String defaultId;

    private RestOperations restOperations = new RestTemplate();

    @Test // 这个调用Controller层接口用的。下面调用的是自己的Redis的接口
    public void TestRestOperations() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String URL = "http://localhost:8090/redisController/setRedis?nameKey=key&nameValue=value";
        ResponseEntity<String> exchange = restOperations.exchange(URL, HttpMethod.GET, entity, String.class);
    }

    @Test // 获取配置文件的map的值，Map<String,Object> value是Object可以是多个子Map的数据
    public void TestConfigurationData() {
        Map map = configurationData.getRptTemperatureSummary();
        String templateName = (String) map.get("template-name");
        String exportName = (String) map.get("export-name");
        Map<String, String> templateMap = (Map<String, String>) map.get("template");
        String com = templateMap.get("common");
        String my = templateMap.get("my_MM");
        System.out.println(my);
        System.out.println(defaultId);
    }

}

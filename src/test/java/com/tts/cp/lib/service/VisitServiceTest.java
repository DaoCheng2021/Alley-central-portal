package com.tts.cp.lib.service;

import com.tts.cp.lib.visit.common.ConfigurationData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

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

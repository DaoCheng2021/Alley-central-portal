package com.tts.cp.lib.visit.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alley zhao created on 2021/9/6.
 */
@Data
@Component
@ConfigurationProperties(prefix = "routine.report")
public class ConfigurationData {

    // 获取配置文件的Map集合的数据
    private Map rptTemperatureSummary;
    private Map rptPestWalkSummary;

}

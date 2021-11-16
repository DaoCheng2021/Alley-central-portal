package com.tts.cp.lib.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Alley zhao created on 2021/8/18.
 */

@Data
@Component
@ConfigurationProperties(prefix = "template1")
public class TemplateIdDefault {

    private Map<String,String> defaultId;
    //配置文件存map集合的数据，取数据
}

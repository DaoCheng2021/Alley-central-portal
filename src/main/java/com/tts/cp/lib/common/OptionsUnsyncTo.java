package com.tts.cp.lib.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/8/18.
 */
@Data
@Component
@ConfigurationProperties(prefix = "options")
public class OptionsUnsyncTo {

    private List<Map<String, String>> test;
    private List<Map<String, String>> test2;
    private List<Map<String, String>> test3;
}

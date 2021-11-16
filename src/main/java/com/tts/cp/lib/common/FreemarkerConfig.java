package com.tts.cp.lib.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class FreemarkerConfig {

    @Value("${icon.email.template-path}")
    private String templatePath;

    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("file:" + templatePath);
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }

    //测试全搜索
    //测试全

    //测试全
    //测试全
    //测试全

    //测试全
    
}

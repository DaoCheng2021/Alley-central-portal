package com.tts.cp.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Alley zhao created on 2021/7/9.
 */
@SpringBootApplication
@EnableSwagger2
@ServletComponentScan
@EnableScheduling //定时任务注解
public class AlleyCentralPortal {

    public static void main(String[] args) {
        SpringApplication.run(AlleyCentralPortal.class, args);
    }

    //这个项目交给GitHub来进行管理，每次上下班都要pull、 push一下
}

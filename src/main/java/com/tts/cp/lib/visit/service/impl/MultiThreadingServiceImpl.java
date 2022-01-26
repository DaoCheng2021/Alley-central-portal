package com.tts.cp.lib.visit.service.impl;

import com.tts.cp.lib.visit.service.MultiThreadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Alley zhao created on 2021/12/28.
 */
@Slf4j
@Service
public class MultiThreadingServiceImpl implements MultiThreadingService {

    /*
     * 此注解加到类上表示这个类所有方法都是多线程处理。加到方法上表示此方法是多线程，主线程到这里可以直接返回，子线程接着处理下面的代码
     * 开启多线程要在启动类上加上@EnableAsync注解
     * */
    @Async // 多线程注解
    @Override
    public void testMultiThreading() {
        for (int i = 0; i < 10; i++) {
            log.info("子线程启动：{}", i);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("子线程结束");
    }
}

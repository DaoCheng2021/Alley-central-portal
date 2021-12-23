package com.tts.cp.lib.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Alley zhao created on 2021/10/18.
 */
@Slf4j
@Service //此类必须要@Service注解
public class TimedTaskCron {

    int i = 1;

    //启动SpringBoob项目规定的时间自动运行 @Scheduled(cron = "0 0 0/1 * * ? ")
    //定时任务cron表达式，（）里面依次代表：秒 分 小时 天 月 星期，
    //@Scheduled(cron = "0 1 * * * *") // 每个小时的第一分钟触发
    //@Scheduled(cron = "0 0 9 13 7 *") // 每年的七月十三号上午九点整触发一次，一年就触发这一次
    //@Scheduled(cron = "0 0 9 * * 1") // 这个代表每周一的九点0分0秒发邮件
    //@Scheduled(cron = "0 1/10 * * * *") //代表每隔十分钟就触发
//    @Scheduled(cron = "0 0 0/1 * * *") // 每隔一小时启动定时任务
    @Scheduled(cron = "0/5 * * * * *") // 每隔五秒启动任务触发
    private void TimedTaskMinute2() {
        log.info("TimedTaskMinute");
        System.out.println("执行：" + i);
        i++;
        log.info("TimedTaskMinute finish");
    }


}

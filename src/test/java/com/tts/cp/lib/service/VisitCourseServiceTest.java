package com.tts.cp.lib.service;

import com.tts.cp.lib.common.RedisUtil;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.bean.SpValidation;
import com.tts.cp.lib.visit.service.CourseService;
import com.tts.cp.lib.visit.service.MultiThreadingService;
import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Alley zhao created on 2021/9/3.
 */
//于数据库交互，SQL，存储过程演示
@Slf4j
@SpringBootTest
public class VisitCourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MultiThreadingService multiThreadingService;


    //
//    public String createJWT(String id,String subject){
//        //加密算法
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        //设置时间
//        Calendar calendar = Calendar.getInstance();
//        Date date = calendar.getTime();
//        //过期时间
//        calendar.setTimeInMillis(date.getTime());
//        Date expirationDate = calendar.getTime();
//        SecretKey secretKey = generalKey();
//        JwtBuilder builder = Jwts.builder()
//                .setId(id)
//                .setIssuedAt(date)
//                .setSubject(subject)
//                .signWith(signatureAlgorithm,secretKey)
//                .setExpiration(expirationDate);
//        return builder.compact();
//    }
    /*
    创建多线程的方法，继承Thread.run & 实现Runnable.run 注解方式：给启动类加@EnableAsync注解，配置线程池，再给需要创建多线程的方法上面加@Async
    解决多线程线程安全的问题，使用synchronized关键字。或者使用Lock锁手动获取和释放锁

    * */
    private Lock lock = new ReentrantLock(); //创建Lock锁

    private void method(Thread thread) {
        lock.lock(); // 获取锁
        try {
            if (lock.tryLock(10, TimeUnit.SECONDS)) {
                try {
                    System.out.println("线程名：" + thread.getName() + "获取了锁");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("线程名：" + thread.getName() + "释放了锁");
                    lock.unlock(); // 释放锁
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test34() {
        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(() -> method(Thread.currentThread()));
            Thread thread2 = new Thread(() -> method(Thread.currentThread()));
            thread1.start();
            thread2.start();
        }
    }

    Integer count = 0;

    public synchronized void testCount() {
        count++;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

    @Test
    public void test33() {
        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(() -> {
                testCount();
            });
            thread1.start();
            Thread thread2 = new Thread(() -> {
                testCount();
            });
            thread2.start();
            testCount();
        }

    }

    //让子线程休眠sleep,但是主线程会先执行完，所以控制器值只打印主线程的东西，没等子线程sleep完就结束Junit了，最终不会显示子线程的休眠后的东西。
    //解决 要不测试时候启动此项目直接用浏览器访问这个Collection方法调用这个方法，要不也给主线程Sleep一下
    @Test
    public void test32() {
        //多线程 Thread
        log.info("主线程开始");
        Thread thread = new Thread(() -> {
            System.out.println("ss");
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ss");
        });
        thread.start(); // 通过start()来启动线程
        log.info("主线程结束");
    }

    @Test// 多线程测试  @Async多线程注解
    public void test30() {
        log.info("主线程");
        multiThreadingService.testMultiThreading();
        log.info("主线程完毕");
    }

    @Test //返回的数据是一个字段多条数据，可以用这样的List<String>来接收
    public void TestGetSpTestAlley3() {
        StandardResponse sr = courseService.getSpTestAlley3("PHI.MST.NSO.2021.01");
        List<String> list = (List<String>) sr.getData();
        System.out.println(list);
    }

    @Test // 返回三个结果，第一个是一个字段多条数据，第二个是一个字段一个数据，第三个是多个Bean的List
    public void TestGetSpTestAlley3_old() {
        StandardResponse sr = courseService.getSpTestAlley3_old("PHI.MST.NSO.2021.01");
        Map<String, Object> map = (Map<String, Object>) sr.getData();
        List<String> list = (List<String>) map.get("itemId");
        List<String> data = (List<String>) map.get("data");
        List<LibItemsMini> libItemsMinis = (List<LibItemsMini>) map.get("libItemsMinis");
        System.out.println("");
    }

    @Test // 根据返回的bmu数据来分类显示
    public void TestGetAvailableMarketsByUserAndBrand() {
        StandardResponse sr = courseService.getAvailableMarketsByUserAndBrand("ttsphsa08", "PHI");
        List list = (List) sr.getData();
        System.out.println("");
    }

    @Test
    public void TestGetSpValidation() {
        StandardResponse sr = courseService.getSpValidation("ACE", "VAL1635474493301XRWU", "Translation");
        Map map = (Map) sr.getData();
        LinkedHashMap<String, List<SpValidation>> map1 = (LinkedHashMap<String, List<SpValidation>>) map.get("collect1");
        Map<String, List<SpValidation>> map2 = (Map<String, List<SpValidation>>) map.get("collect2");
    }

    @Test
    public void RedisTest02() {
        Set<String> a = redisUtil.keys("libItemsMini22232");
        System.out.println(a);
        System.out.println(redisUtil.hasKey("libItemsMini"));
        redisUtil.del("a");
        redisUtil.expire("list", 2000000);
        redisUtil.getExpire("test");
        redisUtil.hasKey("test");
        redisUtil.del("list1");
        boolean set = redisUtil.set("set", "普通缓存放入");
        boolean setnx = redisUtil.setnx("set3", "HEH");
        String set2 = (String) redisUtil.get("set");
        boolean set1 = redisUtil.set("set4", "设置过期时间", -1);
        boolean set3 = redisUtil.set("set4", "设置过期时间", -1);
    }

    @Test// 从Redis拿数据，没有数据就从数据库拿数据，顺便再存进去redis
    public void TestGetRedisData() {
        StandardResponse sr = courseService.getRedisData("PHI.MST.T1129.INFO.3B238794B0", "INF0000001");
        LibItemsMini libItemsMini = (LibItemsMini) sr.getData();
    }

    @Test
    public void TestUpdateRedisData() {
        StandardResponse sr = courseService.updateRedisData("PHI.MST.T1129.INFO.3B238794B0", "INF0000001");
    }

    @Test // 功能：点赞,author所有的article点赞的总数量，用户点赞的文章
    public void TesAddPraise() {
        String articleId = "article02"; // author写的文章唯一id（点赞的具体东西）
        String userId = "userId02"; // 点赞的用户Id
        String authorId = "authorId01";  //author
        StandardResponse sr = courseService.addPraise(articleId, userId, authorId);
        Map map = (Map) sr.getData();
        System.out.println("现在这个" + authorId + "的这一篇文章点赞数量：" + map.get("finalPraise"));
        System.out.println("现在这个" + authorId + "的这一篇文章总点赞数量：" + map.get("praiseCount"));
        System.out.println("现在这个" + userId + "点赞的文章的数量：" + map.get("userPraiseCount"));

    }

    @Test // 用户取消点赞 规定时间保存到数据库
    public void TestCancelPraise() {
        String articleId = "article02"; // author写的文章唯一id（点赞的具体东西）
        String userId = "userId01"; // 取消点赞的用户Id
        String authorId = "authorId01";  //author
        StandardResponse sr = courseService.cancelPraise(userId, articleId, authorId);
    }

    @Test // 微信投票，一个微信一分钟只能给一个author投票一次，过了一分钟才能投票下一次
    public void TestAddVote() {
        String userSoleId = "userSoleId01";
        String authorSoleId = "authorSoleId01";
        String result = courseService.addVote(userSoleId, authorSoleId);
        System.out.println(result);
    }

    @Test
    public void test01(){
        System.out.println(courseService.test("test----"));
    }

}

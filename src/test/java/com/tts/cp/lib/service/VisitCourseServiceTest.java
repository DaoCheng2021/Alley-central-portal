package com.tts.cp.lib.service;

import com.tts.cp.lib.common.RedisUtil;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.bean.SpValidation;
import com.tts.cp.lib.visit.service.CourseService;
import com.tts.lib.web.StandardResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * @author Alley zhao created on 2021/9/3.
 */
//于数据库交互，SQL，存储过程演示
@SpringBootTest
public class VisitCourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

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
    public void RedisTest01() {
        LibItemsMini libItemsMini = new LibItemsMini();
        libItemsMini.setVersionId("left");
        libItemsMini.setItemId("right");
        redisTemplate.opsForValue().set("libItemsMini", libItemsMini);
        System.out.println(redisTemplate.opsForValue().get("libItemsMini"));
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

    @Test
    public void RedisTest03() {
//        long set5 = redisUtil.incr("set5", 1);
//        long set51 = redisUtil.incr("set5", 2);
//        long set52 = redisUtil.decr("set5", 1);
        Map map = new HashMap();
        map.put("map1", "value");
        map.put("map2", "value2");
        map.put("map3", "value3");
//        boolean map1 = redisUtil.hmset("map1", map);
//        String stringt= (String) redisUtil.hget("map1","map2");
//        Map<Object, Object> map1 = redisUtil.hmget("map1");
//        boolean map2 = redisUtil.hmset("map2", map, 10000);
//        boolean hset = redisUtil.hset("map3", "key", "value");
//        boolean hset2 = redisUtil.hset("map3", "key2", "value2",1000);
//        redisUtil.hdel("map3","key","key2");
//        boolean b = redisUtil.hHasKey("map2", "he");
//        boolean hset = redisUtil.hset("map2", "map4", 1);
//        double hincr = redisUtil.hincr("map2", "map4", 3);
//        double hdecr = redisUtil.hdecr("map2", "map4", 1);
//        long l = redisUtil.sSet("set", "value1", "value2", "value3");
//        Set<Object> set = redisUtil.sGet("set");
//        boolean b = redisUtil.sHasKey("set", "value2");
//        long l1 = redisUtil.sSetAndTime("set2", 100, "value1", "value2");
//        long set2 = redisUtil.sGetSetSize("set");
//        long l = redisUtil.setRemove("set", "value2", "value3");

        List list = new ArrayList();
        list.add("listData1");
        list.add("listData2");
        list.add("listData3");
        list.add("listData4");
        list.add("listData5");
        List lis2 = new ArrayList();
        list.add("listData1");
        list.add("listData2");
        list.add("listData3");
        list.add("listData4");
        list.add("listData5");
        List finalList = new ArrayList();
        finalList.add(list);
        finalList.add(lis2);
//        boolean list1 = redisUtil.lSet("list", list);
//        List<Object> list2 = redisUtil.lGet("list", 1, 3);
//        long list3 = redisUtil.lGetListSize("list");
//        Object list5 = redisUtil.lGetIndex("list", -3);
//        boolean list2 = redisUtil.lSet("list2", finalList);
        long l = redisUtil.lRemove("list", 2, "listData5");
        boolean s = redisUtil.set("卧龙", "出师未捷身先死，长使英雄泪满襟！");
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
    public void test01() {
//        redisUtil.lSet("li","li23");
        redisUtil.lRemove("li", 1, "li23");
    }


}

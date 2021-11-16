package com.tts.cp.lib.service;

import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.service.CourseService;
import com.tts.lib.web.StandardResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/9/3.
 */
//于数据库交互，SQL，存储过程演示
@SpringBootTest
public class VisitCourseServiceTest {

    @Autowired
    private CourseService courseService;

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

}

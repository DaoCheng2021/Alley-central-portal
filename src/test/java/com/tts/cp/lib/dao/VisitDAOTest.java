package com.tts.cp.lib.dao;

import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.dao.*;
import com.tts.lib.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Alley zhao created on 2021/9/6.
 */
@Slf4j
@SpringBootTest
public class VisitDAOTest {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private UserBrandCountryRepository countryRepository;

    @Autowired
    private ConfPerformRepository confPerformRepository;

    @Autowired
    private TableDAO tableDAO;

    @Autowired
    private LibItemsMiniRepository libItemsMiniRepository;

    @Test
    public void test01(){
        System.out.println(IdUtils.getId(""));
        System.out.println(IdUtils.getId(""));
        System.out.println(IdUtils.getId(""));
        System.out.println(IdUtils.getId(""));
    }

    @Test // 运用stream.filter流式编程加过滤器来判断List有没有TemplateId=HCV的数据，isEmpty有值就是false，空就是true
    public void TestFindAllByBrand() {
        List<ConfPerform> confPerformList = confPerformRepository.findAllByBrand("KFC");
        List<ConfPerform> confPerforms = confPerformList.stream().filter(conf -> conf.getTemplateId().equalsIgnoreCase("HCV2")).collect(Collectors.toList());
        boolean empty = confPerforms.isEmpty();
        System.out.println(empty);
    }

    @Test // 返回的两个以上的字段，并且多条数据，用这个  或者用List<User>接收
    public void TestGetLanguageView() {
        Map<String, List> map = courseDAO.getLanguageView("KFC");
        List<Map<String, String>> templateList = map.get("templateList");
        List<Map<String, String>> languageList = map.get("languageList");
        String templateId = templateList.get(0).get("templateId");
        String performTypeDesc = templateList.get(0).get("performTypeDesc");
        // templateId performTypeDesc是字段名
        System.out.println(templateId);
        System.out.println(performTypeDesc);
    }

    @Test // 批量更新多条数据，根据传几个参数动态更新几个字段
    public void TestUpdates() {
        ConfPerform confPerform = new ConfPerform();
        confPerform.setTemplateId("_DFKFC");
        confPerform.setUserRoles("test2");
        confPerform.setName("test");
        confPerform.setCreatedBy("Alley");
        confPerform.setCreateDate(new Date());

        ConfPerform confPerform2 = new ConfPerform();
        confPerform2.setTemplateId("_DFPHI");
        confPerform2.setUserRoles("test2");
        confPerform2.setName("test");
        confPerform2.setCreatedBy("Alley");
        confPerform2.setCreateDate(new Date());

        List<ConfPerform> list = new ArrayList();
        list.add(confPerform);
        list.add(confPerform2);
        String userRoles = "userRoles";
        String name = "name";
        int update = courseDAO.update(list, userRoles, name);
        if (update == list.size()) {
            System.out.println("成功");
        }
    }

    @Test// 更新一条数据,根据传几个参数动态更新几个字段
    public void TestUpdate() {
        ConfPerform confPerform = new ConfPerform();
        confPerform.setTemplateId("_DFKFC");
        confPerform.setUserRoles("test2");
        confPerform.setName("test");
        confPerform.setCreatedBy("Alley");
        confPerform.setCreateDate(new Date());
        String userRoles = "userRoles";
        String name = "name";
        int update = courseDAO.update(confPerform, userRoles, name);
        if (update == 1) {
            System.out.println("成功");
        }
    }

    @Test
    public void TestFindByAllPaging() {
        Set<String> set = new HashSet<>();
        set.add("PHI");
        set.add("KFC");
        List<ConfPerform> confPerforms = courseDAO.findByAllPaging(set, "t", "name", 1, 2);
        System.out.println(confPerforms.size());
    }

    @Test
    public void TestFindByAllTemplateId() {
        ConfPerform confPerform = courseDAO.findByAllTemplateId("ECTEST01", "ectest02", "ECTEST01");
        System.out.println(confPerform);
    }

    @Test
    public void TestFindByName() {
        List<String> list = courseDAO.findByName("a");
        System.out.println(list);
    }

    @Test //
    public void TestUpdateConfPerform() {
        confPerformRepository.updateConfPerform("_DFKFC");
    }

    @Test
    public void TestGetConfPerformAll() {
        Set<String> set = new HashSet<>();
        set.add("test2");
        set.add("ACE");
        List<ConfPerform> list = confPerformRepository.getConfPerformAll("PHI", set);
        System.out.println(list.size());
    }

    @Test //插入或者更新多条字段
    public void TestInsertConfPerform() {
        Set<String> set = new HashSet<>();
        set.add("test2");
        set.add("ACE");
        List<ConfPerform> confPerformList = confPerformRepository.getConfPerformAll("PHI", set);
        ConfPerform confPerform = confPerformList.get(1);
        confPerform.setTemplateId("Alley_01");
        System.out.println(courseDAO.insertConfPerform(confPerform));
    }

    @Test//插入或者更新多条字段
    public void TestUpdateBy() {
        ConfPerform confPerform = new ConfPerform();
        confPerform.setTemplateId("Alley_01");
        confPerform.setName("Alley_01");
        confPerform.setPerformType("Alley_01");
        confPerform.setDisplayOrder(666);
        System.out.println(courseDAO.updateBy(confPerform));
    }

    @Test
    public void TestGetConfPerformNamePerformType() {
        List<Map<String, String>> list = confPerformRepository.getConfPerformNamePerformType();
        System.out.println(list.get(1));
    }

    @Test
    public void TestUpdateBatch() {
        ConfPerform confPerform = new ConfPerform();
        confPerform.setTemplateId("Alley_01");
        confPerform.setName("Alley_02");
        confPerform.setPerformType("Alley_02");
        confPerform.setDisplayOrder(777);

        ConfPerform confPerform2 = new ConfPerform();
        confPerform2.setTemplateId("TSV");
        confPerform2.setName("Alley_02");
        confPerform2.setPerformType("Alley_02");
        confPerform2.setDisplayOrder(7777);

        List<ConfPerform> list = new ArrayList<>();
        list.add(confPerform);
        list.add(confPerform2);
        int i = courseDAO.updateBatch(list);
        if (list.size() == i) {
            System.out.println("更新成功");
        }
    }

    @Test
    public void TestGetConfPerformList() {
        ConfPerform confPerform = new ConfPerform();
        confPerform.setTemplateId("_DFKFC");
        confPerform.setName("test");
        confPerform.setPerformType("test");
        confPerform.setDisplayOrder(777);
        ConfPerform confPerformList = courseDAO.getConfPerformList(confPerform);
        System.out.println(confPerformList);
    }

    @Test //直接用@Query写sql一样调用Course，又学了一招
    public void TestExecCourse() {
        List<String> list = confPerformRepository.execCourse("PHI.MST.NSO.2021.01");
        System.out.println(list);
    }

    @Test // 条件是Set<id>,查询多条数据
    public void TestGetConfPerformList2() {
//        Set<String> set = new HashSet<>();
        List<String> set=new ArrayList<>();
        set.add("ECV3");
        set.add("ECTEST01");
        set.add("ECTESTV1");
        set.add("ECV2");
        List<ConfPerform> confPerformList = courseDAO.getConfPerformList("PHI", set);
        System.out.println(confPerformList.size());
    }

    //Test 10 21
    @Test
    public void getConfPerformName() {
        List<String> name = courseDAO.getConfPerformName("PHI");
        System.out.println(name.toString());
    }

    @Test
    public void getConfPerformAll() {
        Set<String> set = new HashSet<>();
//        set.add("_DFKFC");
//        set.add("_DFPHI");
//        set.add("ACE");
        set.add("ACE_ALLEY2222");
        List<ConfPerform> confPerformList = tableDAO.getConfPerformAll("brand", set);
        System.out.println(confPerformList.size());
        if (confPerformList.isEmpty()) {
            System.out.println("空");
        } else {
            System.out.println("有值");
        }
    }

    @Test
    public void TestFindAllByDeleted() {
        List<LibItemsMini> allByDeleted = libItemsMiniRepository.findAllByDeleted(false);
        System.out.println("s");
    }

    @Test
    public void TestFindAllByDataType() {
        List<LibItemsMini> findAllByDataType = libItemsMiniRepository.findAllByDataType("category");
        Set<LibItemsMini> collect = findAllByDataType.stream().filter(list -> "AUP.MST.ACE.FS.2020.02".equals(list.getVersionId())).collect(Collectors.toSet());
        System.out.println("");
    }

}

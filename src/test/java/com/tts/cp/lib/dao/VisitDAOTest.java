package com.tts.cp.lib.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.bean.LibTemplateScope;
import com.tts.cp.lib.visit.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import sun.nio.cs.ext.MacArabic;

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
    public void test01() {
        String[] strings = new String[]{"aa", null, "cc"};
        List<String> list = Arrays.asList(strings);
        Set<String> set = new HashSet<>(Arrays.asList(strings));
        List<String> list1 = new ArrayList<>(set);
        String[] objects = (String[]) list.toArray();
        Stack<String> stack = new Stack<>();
        stack.add("aa");
        stack.add("aa");
        stack.add("aa2");
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(3);
        treeSet.add(2);
        treeSet.add(2);
        Map map = new HashMap();
        map.put("a","a1");
        map.put("a2","a2");
        map.put("a3","a3");
        Set<Map<String,String>> set1 = map.entrySet();
    }

    @Test //  获取List实体类其中一个字段，获取实体类两个字段
    public void test04() {
        List<ConfPerform> confPerforms = confPerformRepository.findAllByBrand("kfc");
        List<String> list = confPerforms.stream().map(ConfPerform::getTemplateType).collect(Collectors.toList());
        LinkedHashMap<Object, Object> collect1 = confPerforms.stream().collect(LinkedHashMap::new, (k, v) -> k.put(v.getTemplateId(), v.getDisplayOrder()), LinkedHashMap::putAll);

    }

    @Test // Optional.ofNullable(Field1).orElse(Field2) 如果Field1是null值,就自动赋值Field2的值，不用if判断空值了
    public void test03() {
        ConfPerform confPerform = confPerformRepository.findByTemplateIdAndDeletedAndTemplateTypeNot("0614", false, "Default");
        String userRoles = Optional.ofNullable(confPerform.getUserRoles()).orElse("");
//        String userRoles = confPerform.getUserRoles();
        if (userRoles.equals("tt")) {
            System.out.println(userRoles);
        }

    }

    @Test
    public void test02() {
        Set<String> set = new HashSet<>();
        set.add("_DFPHI");
        set.add("_DFKFC");
        List<ConfPerform> confPerformList = confPerformRepository.findAllByTemplateId(set);
        List<ConfPerform> confPerformList2 = confPerformRepository.findAllByTemplateId(set);
        for (ConfPerform confPerform : confPerformList) {
            for (ConfPerform confPerform2 : confPerformList2) {
                if (confPerform.getTemplateId().equals(confPerform2.getTemplateId())) {
                    confPerform.setTest(confPerform2.getTemplateId());
                    continue;
                }
            }
        }

        List<ConfPerform> dfkfc = confPerformList.stream().filter(conf -> conf.getTemplateId().equals("_DFKFC")).collect(Collectors.toList());

    }

//    @Test // 实体类转json，jsonString获取里面的数据
//    public void TestFindByTemplateId() throws JsonProcessingException {
//        ConfPerform confPerform = confPerformRepository.findByTemplateId("PPAA342081");
//        String confPerformJSON = new ObjectMapper().writeValueAsString(confPerform); // 实体转JSON
//        JSONObject jsonObject = JSONObject.fromObject(confPerformJSON); //json获取里面的数据
//        String templateId = jsonObject.getString("templateId"); // 获取json里面templateId的数据
//
//    }

    @Test// DAO的sql代码用in来查询返回的结果会有顺序的问题，这个dao这样写避免了顺序的问题
    public void TestGetLibItemsMiniItems() {
        Set set = new HashSet();
        set.add("PIC0000648");
        set.add("PIC0000645");
        set.add("PIC0000634");
        set.add("PIC0000635");
        set.add("PIC0000636");
        String itemIdString = StringUtils.collectionToCommaDelimitedString(set);
        List<LibItemsMini> libItemsMiniItems = courseDAO.getLibItemsMiniItems("AUP.MST.ACE.ATT.2020.01", set, itemIdString);
//        libItemsMiniItems.stream().filter()
    }

    @Test // 如果返回结果只有两个字段、三个字段，可以用这样的DAO 返回类型是List<Map<String,Object>，DAO层用new ColumnMapRowMapper() 类型的接收
    public void TestGetLibItemsMiniItemId() {

        LibItemsMini libitemsmini = libItemsMiniRepository.findByVersionIdAndItemId("PHI.MST.T1129.INFO.3B238794B0", "INF0000001");
        try { // 实体类转JSON
            String stringJSON = new ObjectMapper().writeValueAsString(libitemsmini);
            // JSON转实体类
            LibItemsMini libItemsMini = new ObjectMapper().readValue(stringJSON, LibItemsMini.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> itemIdList = courseDAO.getLibItemsMiniItemId("PHI.MST.T1129.5CB429EA92");
        System.out.println(itemIdList.get(0).get("item_id") + ":" + itemIdList.get(0).get("desc0"));
    }

    @Test // 运用stream.filter流式编程加过滤器来判断List有没有TemplateId=HCV的数据，isEmpty有值就是false，空就是true
    public void TestFindAllByBrand() {
        List<ConfPerform> confPerformList = confPerformRepository.findAllByBrand("PHI");
        // 用stream来操作获取List<USER>其中一条数据一个字段的数据，也可以用list.for循环
        String icon = confPerformList.stream().filter(conf -> conf.getTemplateId().equalsIgnoreCase("T1129")).collect(Collectors.toList()).get(0).getIcon();
        System.out.println(icon); // ep-iconfont-other-equip
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
        List<String> set = new ArrayList<>();
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
        set.add("10254");
        set.add("123");
        set.add("_DFKFC");
        List<String> templateIdList = tableDAO.getConfPerformAll("PHI", set);
        if (templateIdList.isEmpty()) {
            System.out.println("空");
        } else {
            System.out.println("有值");
            System.out.println(templateIdList);
        }
    }

    @Test //Stream
    public void TestFindAllByDeleted() {
        List<LibItemsMini> allByDeleted = libItemsMiniRepository.findAllByDeleted(false);
//        Set<String> collect1 = allByDeleted.stream().map(m -> m.getItemId()).collect(Collectors.toSet());
        Set<String> set = new HashSet<>();

    }

    @Test
    public void TestFindAllByDataType() {
        List<LibItemsMini> findAllByDataType = libItemsMiniRepository.findAllByDataType("category");
        if (null == findAllByDataType || findAllByDataType.isEmpty()) {

        }
        Set<LibItemsMini> collect = findAllByDataType.stream().filter(list -> "AUP.MST.ACE.FS.2020.02".equals(list.getVersionId())).collect(Collectors.toSet());
        System.out.println("");
    }


    @Test
    public void TestGetConfPerform() {
        List<ConfPerform> confPerformList = tableDAO.getConfPerform("ace");
    }

    @Autowired
    private LibTemplateScopeRepository libTemplateScopeRepository;

    @Test
    public void TestGetLibTemplateScope() {
        LibTemplateScope libTemplateScope = tableDAO.getLibTemplateScope("10254", "PHI", "ABW");
        libTemplateScope.setBrand("testaaa2");
        libTemplateScopeRepository.save(libTemplateScope);

//        int i = tableDAO.insertLibTemplateScope(libTemplateScope);
    }


}

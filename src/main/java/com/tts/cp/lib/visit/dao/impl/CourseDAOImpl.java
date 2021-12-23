package com.tts.cp.lib.visit.dao.impl;

import com.tts.cp.lib.common.AlleyUtils;
import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.bean.SpValidation;
import com.tts.cp.lib.visit.dao.CourseDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

/**
 * @author Alley zhao created on 2021/9/3.
 */
@Slf4j
@Component
public class CourseDAOImpl implements CourseDAO {

    @Autowired
    @Qualifier("jdbcTemplate") // 这里JdbcTemplate如果这样开头大写的话，就会报错
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier(value = "namedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcCall SpGetSpTestAlley3;
    private SimpleJdbcCall SpGetSpTestAlley3_old;
    private SimpleJdbcCall SpGetLanguageView;
    private SimpleJdbcCall SpValidationCall;

    private final static RowMapper<LibItemsMini> LIB_ITEMS_MINI = BeanPropertyRowMapper.newInstance(LibItemsMini.class);
    private final static RowMapper<ConfPerform> CONF_PERFORM = BeanPropertyRowMapper.newInstance(ConfPerform.class);

    @Autowired
    private void initSP(@Qualifier("dataSource") DataSource dataSource) {
        this.SpGetSpTestAlley3 = new SimpleJdbcCall(dataSource).withProcedureName("sp_test_Alley3_20210826")
                .declareParameters(new SqlReturnResultSet("itemId", new SingleColumnRowMapper<>(String.class)));

        this.SpGetSpTestAlley3_old = new SimpleJdbcCall(dataSource).withProcedureName("sp_test_Alley3_20210826")
                .declareParameters(
                        new SqlReturnResultSet("itemId", new SingleColumnRowMapper<>(String.class)),
//                        new SqlReturnResultSet("data", new SingleColumnRowMapper<>(String.class)),
                        new SqlReturnResultSet("data", new SingleColumnRowMapper<String>()), // 好像也可以这样
                        new SqlReturnResultSet("libItemsMini", LIB_ITEMS_MINI)
                );

        // 返回多个字段，多条数据，List<Map<String, Object>>用这个接收
        this.SpGetLanguageView = new SimpleJdbcCall(dataSource).withProcedureName("sp_get_language_view")
                .declareParameters(
                        new SqlReturnResultSet("templateList", new ColumnMapRowMapper()),
                        new SqlReturnResultSet("languageList", new ColumnMapRowMapper()));

        this.SpValidationCall = new SimpleJdbcCall(dataSource).withProcedureName("sp_validation")
                .declareParameters(new SqlReturnResultSet("validation", new SingleColumnRowMapper<>(SpValidation.class)));
    }

    @Override
    public List<String> getSpTestAlley3(String versionId) {
        log.info("getSpTestAlley3:{}", versionId);
        try {
            Map<String, Object> execute = SpGetSpTestAlley3.execute(versionId);
            List<String> list = (List<String>) execute.get("itemId");
            return list;
        } catch (Exception e) {
            log.error("Error getSpTestAlley3:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public Map getSpTestAlley3_old(String versionId) {
        log.info("getSpTestAlley3_old:{}", versionId);
        try {
            Map<String, Object> execute = SpGetSpTestAlley3_old.execute(versionId);
            List<String> itemId = (List<String>) execute.get("itemId");
            List<String> data = (List<String>) execute.get("data");
            List<LibItemsMini> libItemsMinis = (List<LibItemsMini>) execute.get("libItemsMini");
            Map<String, Object> mapReturn = new HashMap<>();
            mapReturn.put("itemId", itemId);
            mapReturn.put("data", data);
            mapReturn.put("libItemsMinis", libItemsMinis);
            return mapReturn;
        } catch (Exception e) {
            log.error("Error getSpTestAlley3_old:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public Map<String, List> getLanguageView(String brand) {
        log.info("Error getLanguageView:{}", brand);
        Map<String, Object> execute = SpGetLanguageView.execute(brand);
        List<Map<String, Object>> templateList = (List<Map<String, Object>>) execute.get("templateList");
        List<Map<String, Object>> languageList = (List<Map<String, Object>>) execute.get("languageList");
        Map<String, List> map = new HashMap<>();
        map.put("templateList", templateList);
        map.put("languageList", languageList);
        return map;
    }

    private final static String UPDATE_SQL = "UPDATE conf_perform_test_Alley SET %1$s" +
            " create_date=:createDate,created_by=:createdBy where template_id=:templateId";

    @Override // 批量更新表
    public int update(List<ConfPerform> confPerforms, String... field) {
        log.info("updates:{}", confPerforms.size());
        String[] fields = field;

        if (fields.length == 0) {
            return 0;
        }
        String newField = "";
        for (String s : fields) {
            newField += AlleyUtils.underscoreName(s) + "=:" + s + ",";
        }
        log.info("newField", newField);
        String finalSQL = String.format(UPDATE_SQL, newField); // 合成update的SQL
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(finalSQL, SqlParameterSourceUtils.createBatch(confPerforms.toArray()));
        int returnInt = 0;
        for (int anInt : ints) {
            returnInt += anInt;
        }
        return returnInt;
    }

    //更新一条数据，调用更新多条数据的接口
    @Override
    public int update(ConfPerform confPerform, String... fields) {
        log.info("update:{}", confPerform.toString());
        try {
            List<ConfPerform> list = new ArrayList<>();
            list.add(confPerform);
            return update(list, fields);
        } catch (Exception e) {
            log.error("Error update:{}", e.getMessage());
            return 0;
        }
    }

//    private final static String SQL_FIND_BY = "SELECT * FROM conf_perform_test_Alley WHERE brand IN (:brands) AND (template_id like '%'+:keyword+'%' " +
//            "OR perform_type_desc like '%'+:keyword+'%') ORDER BY  :limit  OFFSET :start2 ROWS FETCH NEXT :limit ROWS ONLY";

    @Override
    public List<ConfPerform> findByAllPaging(Set<String> brands, String keyword, String property, int start2, int limit) {
        log.info("findByAllPaging:{}", brands);
        String SQL_FIND_BY = "SELECT * FROM conf_perform_test_Alley WHERE brand IN (:brands) AND (template_id like '%'+:keyword+'%' " +
                "OR perform_type_desc like '%'+:keyword+'%') ORDER BY " + property + " OFFSET :start2 ROWS FETCH NEXT :limit ROWS ONLY";
        // 先找包含Brand的数据，在模糊查询template_id和perform_type，最后进行分页，property是列名，start、limit从第几列开始，显示几行
        String s = AlleyUtils.underscoreName("itemId"); // 返回item_id 反向驼峰
        MapSqlParameterSource source = new MapSqlParameterSource(); // 参数有Set<String> List 数据类型的时候用这个
        source.addValue("brands", brands);
        source.addValue("keyword", keyword);
        source.addValue("start2", start2);
        source.addValue("limit", limit);
        source.addValue("property", property);
        List<ConfPerform> query = namedParameterJdbcTemplate.query(SQL_FIND_BY, source, CONF_PERFORM);
        return query;
    }

    private final static String SQL_CONF_PERFORM = "SELECT * FROM conf_perform_test_Alley " +
            "WHERE template_id=:templateId AND name=:name AND perform_type=:performType";

    @Override
    public ConfPerform findByAllTemplateId(String templateId, String name, String performType) {
        log.info("findByAllTemplateId");
        Map map = new HashMap();
        map.put("templateId", templateId);
        map.put("name", name);
        map.put("performType", performType);
        ConfPerform confPerform = namedParameterJdbcTemplate.queryForObject(SQL_CONF_PERFORM, map, CONF_PERFORM);
        return confPerform;
    }

    private final static String SQL_FIND_BY_NAME = "SELECT DISTINCT name FROM conf_perform ";

    @Override
    public List<String> findByName(String templateId) {
        List<String> list = jdbcTemplate.queryForList(SQL_FIND_BY_NAME, String.class);
        return list;
    }

    private final static String SQL_INSERT_CONF_PERFORM = "INSERT INTO conf_perform_test_Alley (perform_type, name, display_order, icon, color, attr, user_roles, enable, template_id, brand, perform_type_desc, unsync_to, enable_reporting, scoring_model, multilingual, copy_from, last_publish_date, last_published_by, last_published_revision, last_update_date, last_updated_by, create_date, created_by, config, classification, template_type) " +
            "VALUES (:performType, :name, :displayOrder, :icon, :color, :attr, :userRoles, :enable, :templateId, :brand, :performTypeDesc, :unsyncTo, :enableReporting, :scoringModel, :multilingual, :copyFrom, :lastPublishDate, :lastPublishedBy, :lastPublishedRevision, :lastUpdateDate, :lastUpdatedBy, :createDate, :createdBy, :config, :classification, :templateType)";

    @Override
    public int insertConfPerform(ConfPerform confPerform) {
        int update = namedParameterJdbcTemplate.update(SQL_INSERT_CONF_PERFORM, new BeanPropertySqlParameterSource(confPerform));
        return update;
    }

    private final static String SQL_UPDATE_BY = "UPDATE conf_perform_test_Alley SET name=:name, perform_type=:performType, display_order=:displayOrder WHERE template_id=:templateId";

    @Override //NamedParameterJdbcTemplate+new BeanPropertySqlParameterSource() 更新多条数据
    public int updateBy(ConfPerform confPerform) {
        int update = namedParameterJdbcTemplate.update(SQL_UPDATE_BY, new BeanPropertySqlParameterSource(confPerform));
        return update;
    }

    private final static String SQL_UPDATE_BATCH = "UPDATE conf_perform_test_Alley SET name=:name, perform_type=:performType, display_order=:displayOrder WHERE template_id=:templateId";

    //一次更新多条数据的多个字段,也可以执行insert，只需要把sql改一下就OK
    @Override
    public int updateBatch(List<ConfPerform> list) {
        int[] ints = namedParameterJdbcTemplate.batchUpdate(SQL_UPDATE_BATCH, SqlParameterSourceUtils.createBatch(list.toArray()));
        int i = 0;
        for (int anInt : ints) {
            i += anInt;
        }
        return i;
    }

    private final static String SQL_CONF_PERFORM_LIST = "SELECT * FROM conf_perform_test_Alley WHERE name=:name AND perform_type=:performType AND template_id=:templateId";

    @Override
    public ConfPerform getConfPerformList(ConfPerform confPerform) {
        log.info("getConfPerformList:{}");
        ConfPerform returnConfPerform = namedParameterJdbcTemplate.queryForObject(SQL_CONF_PERFORM_LIST, new BeanPropertySqlParameterSource(confPerform), CONF_PERFORM);
        return returnConfPerform;
    }

    private final static String SQL_SQL_CONF_PERFORM_LIST2 = "SELECT * FROM conf_perform WHERE brand=:brand AND template_id in (:templateId)";

    @Override
    public List<ConfPerform> getConfPerformList(String brand, List<String> templateId) {
//    public List<ConfPerform> getConfPerformList(String brand, Set<String> templateId) {
        Map map = new HashMap<>();
        map.put("brand", brand);
        map.put("templateId", templateId);
        List<ConfPerform> confPerformList = namedParameterJdbcTemplate.query(SQL_SQL_CONF_PERFORM_LIST2, map, CONF_PERFORM);
        return confPerformList;
    }

    //-------Test 10 21
    private final static String GET_CONF_PERFORM_NAME = "SELECT name FROM conf_perform where brand=?";

    @Override
    public List<String> getConfPerformName(String brand) {
        List<String> list = jdbcTemplate.queryForList(GET_CONF_PERFORM_NAME, String.class, brand);
        return list;
    }

    @Override
    public List<SpValidation> getSpValidation(String templateId, String validationId, String vGroup) {
        log.info("--getSpValidation:{},{},{}", templateId, validationId, vGroup);
        Map<String, Object> execute = SpValidationCall.execute(templateId, validationId, vGroup);
        List<SpValidation> validations = (List<SpValidation>) execute.get("validation");
        return validations;
    }

    private final static String GET_LIB_ITEMS_MINI_ITEM_ID = "SELECT top 10 item_id,desc0 FROM lib_items_mini WHERE version_id=?";

    // 如果只返回两个字段，或者三个字段，可以用这个。返回的List集合里，一个Map里面一个key是一个字段的id，values是数据，可以返回无限字段。
    // List.get(0)获取这一条数据，get("item_id")获取item_id的值，get("desc0")获取desc0的值
    @Override
    public List getLibItemsMiniItemId(String versionId) {
        List<Map<String, Object>> query = jdbcTemplate.query(GET_LIB_ITEMS_MINI_ITEM_ID, new ColumnMapRowMapper(), versionId);
        return query;
    }

    private final static String GET_LIB_ITEMS_MINI_ITEMS="SELECT" +
            " * FROM lib_items_mini WHERE version_id=:versionId AND item_id IN (:itemIdSet) ORDER BY CHARINDEX(item_id,:itemIdString); ";
    //这样写避免了in()查询顺序的问题

    @Override
    public List getLibItemsMiniItems(String versionId, Set<String> itemIdSet,String itemIdString) {
        log.info("--getLibItemsMiniItems:{}");
        Map map=new HashMap();
        map.put("versionId",versionId);
        map.put("itemIdSet",itemIdSet);
        map.put("itemIdString",itemIdString);
        List list = namedParameterJdbcTemplate.query(GET_LIB_ITEMS_MINI_ITEMS, map, LIB_ITEMS_MINI);
        return list;
    }

}




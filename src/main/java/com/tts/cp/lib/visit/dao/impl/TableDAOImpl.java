package com.tts.cp.lib.visit.dao.impl;

import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.bean.LibTemplateScope;
import com.tts.cp.lib.visit.dao.TableDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alley zhao created on 2021/10/21.
 */
@Slf4j
//@Component
@Repository
public class TableDAOImpl implements TableDAO {

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Qualifier("namedParameterJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static RowMapper<ConfPerform> CONF_PERFORM_ROW = BeanPropertyRowMapper.newInstance(ConfPerform.class);
    private final static RowMapper<LibTemplateScope> LIB_TEMPLATE_SCOPE_ROW_MAPPER = BeanPropertyRowMapper.newInstance(LibTemplateScope.class);

    private final static String SQL_GET_CONF_PERFORM_ALL = "SELECT template_id FROM conf_perform WHERE brand=:brand AND template_id IN (:templateId)";

    @Override
    public List<String> getConfPerformAll(String brand, Set<String> templateId) {
        Map map = new HashMap();
        map.put("brand", brand);
        map.put("templateId", templateId);
        List<String> templateIdList = namedParameterJdbcTemplate.queryForList(SQL_GET_CONF_PERFORM_ALL, map, String.class);
        return templateIdList;
    }

    private final static String SQL_GET = "SELECT * FROM conf_perform where template_id in (?)";

    @Override
    public List<ConfPerform> getConfPerform(String templateId) {
        try {
            List<ConfPerform> confPerform = jdbcTemplate.query(SQL_GET, CONF_PERFORM_ROW, templateId);
            return confPerform;
        } catch (DataAccessException e) {
            return null;
        }
    }

    private final static String SQL_LIB_TEMPLATE_SCOPE = "SELECT * FROM lib_template_scope where template_id=? and brand = ? and country_code = ?";

    @Override
    public LibTemplateScope getLibTemplateScope(String templateId, String brand, String countryCode) {
        LibTemplateScope libTemplateScope = jdbcTemplate.queryForObject(SQL_LIB_TEMPLATE_SCOPE, LIB_TEMPLATE_SCOPE_ROW_MAPPER, templateId, brand, countryCode);
        return libTemplateScope;
    }

    private final static String INSERT = "INSERT INTO lib_template_scope (template_id, brand, country_code, enabled, update_by, update_date, bmu)" +
            " VALUES (:templateId, :brand, :countryCode, :enabled, :updateBy, :updateDate, :bmu) ";

    @Override
    public int insertLibTemplateScope(LibTemplateScope libTemplateScope) {
        int update = namedParameterJdbcTemplate.update(INSERT, new BeanPropertySqlParameterSource(libTemplateScope));
        return update;
    }

}

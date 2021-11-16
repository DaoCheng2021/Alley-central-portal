package com.tts.cp.lib.visit.dao.impl;

import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.dao.TableDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alley zhao created on 2021/10/21.
 */
@Slf4j
@Component
public class TableDAOImpl implements TableDAO {

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Qualifier("namedParameterJdbcTemplate")
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static RowMapper<ConfPerform> CONF_PERFORM_ROW = BeanPropertyRowMapper.newInstance(ConfPerform.class);

    private final static String SQL_GET_CONF_PERFORM_ALL = "SELECT * FROM conf_perform WHERE brand=:brand AND template_id IN (:templateId)";

    @Override
    public List<ConfPerform> getConfPerformAll(String brand, Set<String> templateId) {
        Map map = new HashMap();
        map.put("brand", brand);
        map.put("templateId", templateId);
        List<ConfPerform> confPerformList = namedParameterJdbcTemplate.query(SQL_GET_CONF_PERFORM_ALL, map, CONF_PERFORM_ROW);
        return confPerformList;
    }
}

package com.tts.cp.lib.visit.dao;

import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.bean.SpValidation;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alley zhao created on 2021/9/3.
 */
//所有的DAO都要大写'DAO' 并且原则上一个DAOImpl对应一个一个表
public interface CourseDAO {

    List<String> getSpTestAlley3(String versionId);

    Map getSpTestAlley3_old(String versionId);

    Map<String, List> getLanguageView(String brand);

    int update(List<ConfPerform> confPerforms, String... fields);

    int update(ConfPerform confPerform, String... fields);

    List<ConfPerform> findByAllPaging(Set<String> brands, String keyword, String property, int start, int limit);

    ConfPerform findByAllTemplateId(String templateId, String name, String performType);

    List<String> findByName(String templateId);

    int insertConfPerform(ConfPerform confPerform);

    int updateBy(ConfPerform confPerform);

    int updateBatch(List<ConfPerform> list);

    ConfPerform getConfPerformList(ConfPerform confPerform);

//    List<ConfPerform> getConfPerformList(String brand, Set<String> templateId);
    List<ConfPerform> getConfPerformList(String brand, List<String> templateId);

    List<String> getConfPerformName(String brand);

    List<SpValidation> getSpValidation(String templateId, String validationId, String vGroup);

}

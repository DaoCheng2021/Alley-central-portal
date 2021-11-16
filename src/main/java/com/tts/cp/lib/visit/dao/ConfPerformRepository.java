package com.tts.cp.lib.visit.dao;

import com.tts.cp.lib.visit.bean.ConfPerform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alley zhao created on 2021/9/7.
 */
public interface ConfPerformRepository extends JpaRepository<ConfPerform, String> {

    // 原则上一个表对应一个DAO比较好
    @Modifying
    @Transactional // 更新的@Query代码的sql要加这两个注解@Modifying @Transactional
    @Query(value = "UPDATE conf_perform_test_Alley SET create_date=GETDATE() WHERE template_id=?", nativeQuery = true)
    void updateConfPerform(String templateId);

    //参数是Set<String> 或者List<String>用@Param这个注解
    @Query(value = "SELECT * FROM conf_perform_test_Alley WHERE brand=:brand AND perform_type in (:performType) ", nativeQuery = true)
    List<ConfPerform> getConfPerformAll(@Param(value = "brand") String brand, @Param(value = "performType") Set<String> performType);

    //如果返回的两三条字段多条数据，又没有够一个User的数据。就这样写每一条List就是一个条数据，map的key就是字段的名字，value就是data
    @Query(value = "SELECT name , perform_type AS performType ,attr FROM conf_perform_test_Alley", nativeQuery = true)
    List<Map<String, String>> getConfPerformNamePerformType();

    @Query(value = "EXEC sp_test_Alley3_20210826 @version_id=:versionId ", nativeQuery = true)
    List<String> execCourse(@Param("versionId") String versionId);

}

package com.tts.cp.lib.visit.dao;

import com.tts.cp.lib.visit.bean.LibItemsMini;
import com.tts.cp.lib.visit.bean.entityid.LibItemsMiniId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * @author Alley zhao created on 2021/10/22.
 */
public interface LibItemsMiniRepository extends JpaRepository<LibItemsMini, LibItemsMiniId> {

    List<LibItemsMini> findAllByDeleted(boolean deleted);

    @Query(value = "select distinct version_id from lib_items_mini",nativeQuery = true)
    Set<String> aaa();
    @Query(value = "select distinct version_id from lib_items_mini",nativeQuery = true)
    List<String> aaa2();

    List<LibItemsMini> findAllByDataType(String dataType);
}

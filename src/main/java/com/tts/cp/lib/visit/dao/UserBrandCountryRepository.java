package com.tts.cp.lib.visit.dao;

import com.tts.cp.lib.visit.bean.UserBrandCountry;
import com.tts.cp.lib.visit.bean.entityid.UserBrandCountryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Alley zhao created on 2021/9/6.
 */
public interface UserBrandCountryRepository extends JpaRepository<UserBrandCountry, UserBrandCountryId> {

    @Query(value = "SELECT DISTINCT bmu,country_code,country_name,country_name AS name FROM user_brand_country WHERE user_id=?1 AND brand=?2 ORDER BY bmu", nativeQuery = true)
    List<UserBrandCountry> getUserBrandList(String userId, String brand);

}

package com.tts.cp.lib.visit.bean;

import com.tts.cp.lib.visit.bean.entityid.UserBrandCountryId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Alley zhao created on 2021/7/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UserBrandCountryId.class)
@Table(name = "user_brand_country")
public class UserBrandCountry {

    @Id
    private String bmu;
    @Id
    private String countryCode;

    private String countryName;

    private String name;

    @Transient
    private boolean enabled;
}

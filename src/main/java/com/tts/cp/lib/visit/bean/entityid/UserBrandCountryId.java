package com.tts.cp.lib.visit.bean.entityid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alley zhao created on 2021/7/9.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBrandCountryId implements Serializable {

    private String bmu;
    private String countryCode;

}

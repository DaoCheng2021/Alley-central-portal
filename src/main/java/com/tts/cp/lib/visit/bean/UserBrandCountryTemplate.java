package com.tts.cp.lib.visit.bean;

import lombok.Data;

/**
 * @author Alley zhao created on 2021/7/19.
 */
@Data
public class UserBrandCountryTemplate {

    private String bmu;
    private String countryCode;
    private String countryName;
    private String name;
    private boolean enabled;
    //boolean是基本类型,Boolean是一个包装类型
    //boolean一般存在于栈空间中，而Boolean对象存在于堆空间中
    //boolean有true和false两种值，Boolean除了true和false还有null
}

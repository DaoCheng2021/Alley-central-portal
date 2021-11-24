package com.tts.cp.lib.visit.bean;


import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserAccess {
    private String userId;
    private String firstName;
    private String lastName;
    private int userSeqId;
    private String roles;
    private String bmus;
    private boolean success;
    private String token;
    private String countryCodes;
    private String password;
    private List<String> roleList;
    private List<String> visitTypeCodes;
    private String loginType;
    private String avatarData;
    private int avatarUdts;
    private String path;
    private Set<String> brand;
    private String position;


    private String languageCode;
//    private UserProfile userProfile;

    public void setBmus(String bmus) {
        this.bmus = bmus;
        Set<String> bmuList = StringUtils.commaDelimitedListToSet(bmus);
        Set<String> brands = new HashSet<>();
        bmuList.forEach(bmu -> {
            if (bmu.endsWith("K")) brands.add("KFC");
            if (bmu.endsWith("P")) brands.add("PHI");
        });
        this.brand = brands;
    }
}

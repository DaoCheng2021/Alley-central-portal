package com.tts.cp.lib.visit.bean;

import lombok.Data;

import java.util.List;

/**
 * @author Alley zhao created on 2021/8/20.
 */
@Data
public class LibItemsMiniCreateCriteria {

    private List<LibItemsMini> libItemsMiniList;

    private UserAccess userAccess;

}

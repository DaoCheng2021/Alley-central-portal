package com.tts.cp.lib.common;

/**
 * @author Alley zhao created on 2021/7/20.
 */
public enum StandardEnum {


    TRUE_DESC("Success"),

    TRUE_CODE("100"),

    FALSE_DESC("NO DATA"),

    FALSE_CODE("101");

    private final String standardEnum;

    StandardEnum(String standardEnum) {

        this.standardEnum = standardEnum;
    }

    public String getStandardEnum() {
        return standardEnum;
    }
}

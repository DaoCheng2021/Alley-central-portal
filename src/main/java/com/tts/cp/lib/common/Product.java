package com.tts.cp.lib.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Alley zhao created on 2021/11/4.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @JsonProperty("IdId") // 此注解用在属性上，作用于在序列化的时候把Name转换成另外一个Name。Name不规范，驼峰映射有错误使用。
    private int id;
    private int num;
    //ew BigDecimal(100)
    private BigDecimal price;
    private String name;
    private String category;

}

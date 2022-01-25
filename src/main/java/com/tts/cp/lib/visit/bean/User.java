package com.tts.cp.lib.visit.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alley zhao created on 2021/8/30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {


    private String name;
    private String password;
    private int id;

    // 类里面某个字段特别长还没有重要信息，可以重写toString来手动去点这个长的字段
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

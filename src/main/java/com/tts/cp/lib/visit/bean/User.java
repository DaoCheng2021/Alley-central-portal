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

}

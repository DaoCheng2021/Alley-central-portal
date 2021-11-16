package com.tts.cp.lib.visit.bean.entityid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alley zhao created on 2021/7/20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibTemplateScopeId implements Serializable {

    private String templateId;
    private String brand;
    private String countryCode;

}

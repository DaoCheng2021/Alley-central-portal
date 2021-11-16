package com.tts.cp.lib.visit.bean;

import com.tts.cp.lib.visit.bean.entityid.LibTemplateScopeId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author Alley zhao created on 2021/7/20.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LibTemplateScopeId.class)
@Table(name = "lib_template_scope")
public class LibTemplateScope {

    @Id
    private String templateId;
    @Id
    private String brand;
    @Id
    private String countryCode;

    private boolean enabled;


}

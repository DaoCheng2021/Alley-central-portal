package com.tts.cp.lib.visit.bean;

import com.tts.cp.lib.visit.bean.entityid.LibTemplateScopeId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    private String updateBy;
    private Date updateDate;
    private String bmu;

    @Transient
    private String userId;



}

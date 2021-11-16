package com.tts.cp.lib.visit.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/6/29.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conf_perform")
public class ConfPerform {

    @Id
    private String templateId;

    private String performType="";  //create的时候有些字段可能没有赋值，但是数据库这个字段是not null，就要手动给Bean字段的后面设置默认值
    private String name;
    private int displayOrder=0;
    private String icon;
    private String color;
    private String attr;
    private String userRoles;
    private boolean enable=false;
    private String brand;
    private String performTypeDesc;
    private String unsyncTo;
    private boolean enableReporting;
    private String scoringModel;
    private Boolean multilingual;
    private String copyFrom;
    private Date lastPublishDate;
    private String lastPublishedBy;
    private int lastPublishedRevision;
    private Date lastUpdateDate;
    private String lastUpdatedBy;
    private Date createDate;
    private String createdBy;
    private String config;
    private String classification;
    private String templateType;

    @Transient
    private List<Map> unsyncToOptions;

    @Transient
    private List<Map> scoringModelOptions;

    @Transient
    private List<Map> brandOptions;

    @Transient
    private List<ConfPerform> data;

}

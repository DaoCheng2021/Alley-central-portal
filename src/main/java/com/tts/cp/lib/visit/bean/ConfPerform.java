package com.tts.cp.lib.visit.bean;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String performType;
    private String name;
    private int displayOrder;
    private String icon;
    private String color;
    private String attr;
    private String userRoles;
    private boolean enable;
    private String brand;
    private String performTypeDesc;
    private String unsyncTo;
    private boolean enableReporting;
    private String scoringModel;
    private Boolean multilingual;

    @Column(updatable = false)
    private String copyFrom;

    @Column(updatable = false)
    private Date lastPublishDate;
    @Column(updatable = false)
    private String lastPublishedBy;
    @Column(updatable = false)
    private int lastPublishedRevision;

    private Date lastUpdateDate;
    private String lastUpdatedBy;
    private Date createDate;
    private String createdBy;

    @JsonRawValue
    @JsonDeserialize(using = ObjectToStringDeserializer.class)
    private String config;
    private String classification;
    private String templateType;
    private String status = "";
    private boolean deleted = false;

    @Transient
    private List<Map> unsyncToOptions;

    @Transient
    private List<Map> scoringModelOptions;

    @Transient
    private List<Map> brandOptions;

    @Transient
    private boolean starred;

    @Override
    public String toString() {
        return "ConfPerform{" +
                "templateId='" + templateId + '\'' +
                ", performType='" + performType + '\'' +
                ", name='" + name + '\'' +
                ", displayOrder=" + displayOrder +
                ", icon='" + icon + '\'' +
                ", color='" + color + '\'' +
                ", attr='" + attr + '\'' +
                ", userRoles='" + userRoles + '\'' +
                ", enable=" + enable +
                ", brand='" + brand + '\'' +
                ", performTypeDesc='" + performTypeDesc + '\'' +
                ", unsyncTo='" + unsyncTo + '\'' +
                ", enableReporting=" + enableReporting +
                ", scoringModel='" + scoringModel + '\'' +
                ", multilingual=" + multilingual +
                ", copyFrom='" + copyFrom + '\'' +
                ", lastPublishDate=" + lastPublishDate +
                ", lastPublishedBy='" + lastPublishedBy + '\'' +
                ", lastPublishedRevision=" + lastPublishedRevision +
                ", lastUpdateDate=" + lastUpdateDate +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", createDate=" + createDate +
                ", createdBy='" + createdBy + '\'' +
                ", classification='" + classification + '\'' +
                ", templateType='" + templateType + '\'' +
                ", status='" + status + '\'' +
                ", deleted=" + deleted +
                ", unsyncToOptions=" + unsyncToOptions +
                ", scoringModelOptions=" + scoringModelOptions +
                ", brandOptions=" + brandOptions +
                '}';
    }

    @Transient
    private List<ConfPerform> data;

    @Transient
    private String test;

}

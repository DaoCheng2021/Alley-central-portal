package com.tts.cp.lib.visit.bean;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tts.cp.lib.visit.bean.entityid.LibItemsMiniId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alley zhao created on 2021/6/30.
 */
@Entity
@Data
@IdClass(LibItemsMiniId.class)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lib_items_mini")
public class LibItemsMini {

    @Id
    private String versionId;

    @Id
    private String itemId;

    private String parentId = "";
    private String desc0;
    private String desc1;
    private String desc2;
    private String desc3;
    private String dataType = "";
    private String scoring = "";
    private String scoringGroupId;
    private String bizType = "";
    private int bizLevel = 0;
    private String summaryCat;
    private boolean infoQuestion = false;
    private boolean penalty = false;
    private boolean bonus = false;
    private boolean picklistPhotoRequired = false;
    private String safetyCode;
    private String responsibleParty;
    private boolean closureDecision;
    private int displayOrder = 0;
    private String iconId;
    private String imageId;

    private String masterItemId;
    private String appliedScope;
    private boolean required = false;
    private boolean branchItem = false;
    private boolean deleted = false;

    @Transient
    private String branches;

    @Column(updatable = false)
    private Date createDate;
    @Column(updatable = false)
    private String createBy;

    private Date updateDate;
    private String updateBy;
    private Date descUpdateDate;

    @JsonRawValue
    @JsonDeserialize(using = ObjectToStringDeserializer.class)
    private String config;

    @JsonRawValue
    @JsonDeserialize(using = ObjectToStringDeserializer.class)
    private String photoConfig;
    private String answerType = "";

    @Transient
    private List<LibItemsMini> data = new ArrayList<>();

}

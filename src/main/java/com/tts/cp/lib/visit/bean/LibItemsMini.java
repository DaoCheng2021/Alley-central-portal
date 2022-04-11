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
import java.util.HashSet;
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

    /*
        @JsonRawValue这个注解，后端String类型数据，前端需要Object类型的数据，这个注解加上可以转换。
        @JsonDeserialize(using=ObjectToStringDeserializer.class)这个注解是为了前端的Object类型的数据传到后端用String类型接收
    * */
    @JsonRawValue
    @JsonDeserialize(using = ObjectToStringDeserializer.class)
    private String photoConfig;
    private String answerType = "";

    @Transient
    private List<LibItemsMini> data = new ArrayList<>();

    @Transient
    private HashSet hashSet;

}

package com.tts.cp.lib.email.bean;

import com.tts.cp.lib.visit.bean.ConfPerform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author Alley zhao created on 2021/10/27.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pub_request")
public class PubRequest {

    @Id
    private String pubRequestId;
    private String templateId;
    private String languages;
    private String versionIds;
    private String validationId;
    private Date requestDate;
    private String requestedBy;
    private String status;
    private Date statusDate;
    private String failureDetail;
    private int revision;
    private String pubType;
    private String bmus;
    private String enableDataBranch;
    private String comment;
    private String publishTo;

    @Transient
    private String languageNames;

    @Transient
    private ConfPerform template;

}

package com.tts.cp.lib.visit.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tts.cp.lib.common.DateTimeJsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Alley zhao created on 2021/7/20.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lib_version_lang_alley")
public class LibVersionLangAlley implements Serializable {

    @Id
    private String versionId;
    private String languageCode;
    private String language;
    @JsonDeserialize(using = DateTimeJsonDeserialize.class)
    private Date updateDate;
    private String updateBy;

}

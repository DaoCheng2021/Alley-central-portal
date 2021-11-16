package com.tts.cp.lib.email.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "asset_icon")
public class AssetIcon {
    @Id
    private String iconId;
    private String brand;
    private String tags;
    private String name;

    @Column(updatable = false)
    private String serverPath;

    private String status;
    private String comment;
    private boolean deleted;

    @Column(updatable = false)
    private String createdBy;
    @Column(updatable = false)
    private Date createDate;

    private String updatedBy;
    private Date updateDate;
    private boolean devNotificationSent;
    private int userNotificationSent;

    @Transient
    private String email;

}

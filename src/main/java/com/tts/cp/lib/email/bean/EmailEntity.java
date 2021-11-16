package com.tts.cp.lib.email.bean;

import lombok.Data;

import java.io.File;

/**
 * @author Alley zhao created on 2021/10/18.
 */
@Data
public class EmailEntity {

    private String emailSubject; //主题
    private String emailContent; //模板内容
    private String sendTo;       //发送给N个邮箱
    private String bcc;          //密送人 收件人不会看到邮件同时发给密送人
    private String cc;           //抄送人
    private File[] attachments;  //发送的附件
    private String emailKey;     //数据库邮箱的id

}

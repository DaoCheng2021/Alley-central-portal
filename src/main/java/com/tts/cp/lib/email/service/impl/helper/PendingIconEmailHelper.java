package com.tts.cp.lib.email.service.impl.helper;

import com.tts.cp.lib.email.bean.AssetIcon;
import com.tts.cp.lib.email.bean.EmailEntity;
import com.tts.cp.lib.email.dao.AssetIconRepository;
import com.tts.lib.web.StandardResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alley zhao created on 2021/10/18.
 */
@Slf4j
@Service("PendingIconEmailHelper")
public class PendingIconEmailHelper extends AbstractMailHelper {

    // freemarker pom文件里面这个包
    @Qualifier("getFreeMarkerConfiguration")
    @Autowired
    private Configuration freemarkerConfig;

    @Value("${icon.email.template-path}")
    private String templatePath;

    @Value("${icon.email.mail-pending-icon.tpl-name}")
    private String tplName;

    @Value("${icon.email.mail-pending-icon.subject}")
    private String subject;

    @Value("${icon.email.mail-pending-icon.sendto}")
    private String sendTo;

    @Value("${icon.email.bcc}")
    private String bcc;

    @Value("${icon.email.siteTXT}")
    private String siteTXT;

    @Value("${system-setting.picture-url}")
    private String pictureURL;

    @Autowired
    private AssetIconRepository assetIconRepository;

    @Override
    public StandardResponse sendEmail(Map<String, Object> emailParams) {
        log.info("sendEmail");
        System.out.println("继承Abstract抽象类");

        List<AssetIcon> AssetIconList = assetIconRepository.getAllByStatus(pictureURL, "P");// Pending
        EmailEntity emailEntity = new EmailEntity();
        Map map = new HashMap();
        map.put("AssetIconList", AssetIconList);
        String html;
        Template template;
        try {
            template = freemarkerConfig.getTemplate(tplName); //放模板名字
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);

            emailEntity.setEmailSubject(subject);
            emailEntity.setEmailContent(html);
            emailEntity.setSendTo(sendTo);
            emailEntity.setBcc(bcc);
            List<String> list = new ArrayList<>();
            list.add("当你觉得为时已晚，恰恰是最早的时候");
            list.add("二十一天就会养成一种习惯");
            list.add("比亚迪唐，Build Your Dream");
            writeDataToFile(list, siteTXT);
            File[] files = {new File(siteTXT)};
            emailEntity.setAttachments(files);

            this.generalSendEmail(emailEntity); // 调用发邮件的工具类
        } catch (Exception e) {
            e.printStackTrace();
        }
        StandardResponse sr = new StandardResponse();
        sr.setData("yes");
        return sr;
    }


    //这个地址的txt文件如果不存在就删除，如果存在就先清除原有数据，再导入新的数据
    public void writeDataToFile(List<String> list, String siteTXT) {  //传过去list<String>的数据，打印到txt文本上，没个数据都换行
        File file;
        BufferedWriter bw;
        try {
            file = new File(siteTXT);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.flush();
            bw = new BufferedWriter(fw);
            for (String str : list) {
                fw.write(str + "\r\n");
            }
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            log.info(e.getMessage());
            e.printStackTrace();
        }
    }
}

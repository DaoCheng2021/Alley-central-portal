package com.tts.cp.lib.email.service.impl.helper;

import com.tts.lib.web.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Alley zhao created on 2021/10/18.
 */
@Slf4j
@Service("UnapprovedIconEmailHelper")
public class UnapprovedIconEmailHelper extends AbstractMailHelper {


    @Override
    public StandardResponse sendEmail(Map<String, Object> emailParams) throws Exception {
        log.info("sendEmail");
        System.out.println("");
        return null;
    }
}

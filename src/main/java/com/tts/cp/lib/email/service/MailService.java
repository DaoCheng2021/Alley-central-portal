package com.tts.cp.lib.email.service;

import com.tts.lib.web.StandardResponse;

import java.util.Map;

public interface MailService {

    StandardResponse sendEmail(Map<String, Object> emailParams);

}

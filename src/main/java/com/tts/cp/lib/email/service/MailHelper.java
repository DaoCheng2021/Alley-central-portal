package com.tts.cp.lib.email.service;

import com.tts.lib.web.StandardResponse;

import java.util.Map;

public interface MailHelper {

    StandardResponse sendEmail(Map<String, Object> emailParams) throws Exception;

}

package com.tts.cp.lib.visit.service;

import com.tts.lib.web.StandardResponse;

/**
 * @author Alley zhao created on 2021/9/3.
 */
public interface CourseService {

    StandardResponse getSpTestAlley3(String versionId);

    StandardResponse getSpTestAlley3_old(String versionId);

    StandardResponse getAvailableMarketsByUserAndBrand(String userId, String brand);

//    StandardResponse getConfPerform
}

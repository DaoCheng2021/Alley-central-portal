package com.tts.cp.lib.visit.service;

import com.tts.lib.web.StandardResponse;

/**
 * @author Alley zhao created on 2021/9/3.
 */
public interface CourseService {

    StandardResponse getSpTestAlley3(String versionId);

    StandardResponse getSpTestAlley3_old(String versionId);

    StandardResponse getAvailableMarketsByUserAndBrand(String userId, String brand);

    StandardResponse getSpValidation(String templateId, String validationId, String vGroup);

    StandardResponse getRedisData(String versionId, String itemId);

    StandardResponse updateRedisData(String versionId, String itemId);

    StandardResponse addPraise(String articleId, String userId, String UPId);

    StandardResponse cancelPraise(String userId, String articleId, String authorId);

    String addVote(String userSoleId, String authorSoleId);

    String test(String string);



}

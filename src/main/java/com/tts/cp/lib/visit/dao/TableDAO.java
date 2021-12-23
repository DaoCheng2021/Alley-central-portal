package com.tts.cp.lib.visit.dao;

import com.tts.cp.lib.visit.bean.ConfPerform;
import com.tts.cp.lib.visit.bean.LibTemplateScope;

import java.util.List;
import java.util.Set;

/**
 * @author Alley zhao created on 2021/10/21.
 */
public interface TableDAO {

    List<ConfPerform> getConfPerformAll(String brand, Set<String> templateId);

    List<ConfPerform> getConfPerform(String templateId);

    LibTemplateScope getLibTemplateScope(String templateId,String brand,String countryCode);

    int insertLibTemplateScope(LibTemplateScope libTemplateScope);

}

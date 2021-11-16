package com.tts.cp.lib.visit.dao;

import com.tts.cp.lib.visit.bean.ConfPerform;

import java.util.List;
import java.util.Set;

/**
 * @author Alley zhao created on 2021/10/21.
 */
public interface TableDAO {

    List<ConfPerform> getConfPerformAll(String brand, Set<String> templateId);



}

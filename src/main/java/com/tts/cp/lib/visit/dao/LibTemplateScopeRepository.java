package com.tts.cp.lib.visit.dao;

import com.tts.cp.lib.visit.bean.LibTemplateScope;
import com.tts.cp.lib.visit.bean.entityid.LibTemplateScopeId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Alley zhao created on 2021/12/21.
 */
public interface LibTemplateScopeRepository extends JpaRepository<LibTemplateScope, LibTemplateScopeId> {



}

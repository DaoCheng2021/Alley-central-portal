package com.tts.cp.lib.visit.bean.entityid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Alley zhao created on 2021/6/30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibItemsMiniId implements Serializable {

    private String versionId;
    private String itemId;
}

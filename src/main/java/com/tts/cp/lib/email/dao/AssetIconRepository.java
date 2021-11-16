package com.tts.cp.lib.email.dao;

import com.tts.cp.lib.email.bean.AssetIcon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Alley zhao created on 2021/10/19.
 */
public interface AssetIconRepository extends JpaRepository<AssetIcon, String> {

    @Query(value = " select icon_id, ISNULL(brand,'') AS brand, ISNULL(tags,'') AS tags, ISNULL(name,'') AS name,? + server_path AS server_path, status, comment, deleted, created_by, create_date, " +
            " updated_by, update_date, dev_notification_sent, user_notification_sent from asset_icon where status =? ", nativeQuery = true)
    List<AssetIcon> getAllByStatus(String url, String status);

}

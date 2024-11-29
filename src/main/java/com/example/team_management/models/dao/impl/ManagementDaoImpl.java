package com.example.team_management.models.dao.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.team_management.models.dao.ManagementDao;
import com.example.team_management.models.entity.Management;

/**
 * ManagementDaoImplクラス
 * データベース操作を行うクラス
 */
@Repository("ManagementDaoImpl")
public class ManagementDaoImpl implements ManagementDao{

    @Autowired
    JdbcTemplate jdbc;

    /**
     * 進捗データ追加
     * @param management 進捗データ
     * @return 追加された行数
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int addManagementData(Management management) throws DataAccessException {
        int rowNumber = jdbc.update("INSERT INTO management(user_id, subject, link, status,completion_schedule) VALUES (?,?,?,?,?)", 
                                        management.getUser_id(), management.getSubject(), management.getLink(), management.getStatus(), management.getCompletion_schedule());
        return rowNumber;
    }

    /**
     * 完了年データ取得
     * @return 完了年のリスト
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public List<Integer> getComplateYearData() throws DataAccessException {
        List<Integer> years = jdbc.queryForList("SELECT DISTINCT YEAR(delete_at) FROM management WHERE delete_at IS NOT null", Integer.class);
        return years;
    }

    /**
     * 年別完了データ取得
     * @param year 年
     * @return 完了データのリスト
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public List<Management> getAllCompletionManagementData(String year) throws DataAccessException {
        List<Map<String, Object>> geList = jdbc.queryForList("SELECT * FROM management WHERE delete_at IS NOT null AND YEAR(delete_at) = ?",year);
        List<Management> managementList = new ArrayList<>();

        for (Map<String, Object> map : geList){
            Management management = new Management();
            management.setManagement_id((Integer) map.get("management_id"));
            management.setUser_id((Integer) map.get("user_id"));
            management.setSubject((String) map.get("subject"));
            management.setLink((String) map.get("link"));
            management.setStatus((Integer) map.get("status"));
            management.setCreate_at((LocalDateTime) map.get("create_at"));
            management.setUpdate_at((LocalDateTime) map.get("update_at"));
            management.setDelete_at((LocalDateTime) map.get("delete_at"));
            management.setCompletion_schedule((Date) map.get("completion_schedule"));
            managementList.add(management);
        }

        return managementList;
    }

    /**
     * 未完了データ取得
     * @return 未完了データのリスト
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public List<Management> getAllIncompleteManagementData() throws DataAccessException {
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM management WHERE delete_at IS null");
        List<Management> managementList = new ArrayList<>();

        for (Map<String, Object> map : getList){
            Management management = new Management();
            management.setManagement_id((Integer) map.get("management_id"));
            management.setUser_id((Integer) map.get("user_id"));
            management.setSubject((String) map.get("subject"));
            management.setLink((String) map.get("link"));
            management.setStatus((Integer) map.get("status"));
            management.setCreate_at((LocalDateTime) map.get("create_at"));
            management.setUpdate_at((LocalDateTime) map.get("update_at"));
            management.setDelete_at((LocalDateTime) map.get("delete_at"));
            management.setCompletion_schedule((Date) map.get("completion_schedule"));
            managementList.add(management);
        }

        return managementList;
    }

    /**
     * IDによる進捗データ取得
     * @param id 管理ID
     * @return 進捗データ
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public Management getManagementDataById(String id) throws DataAccessException {
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM management WHERE management_id = ?",id);
        Map<String,Object> map = getList.isEmpty() ? null : getList.get(0);
        Management management = new Management();

        if (map != null){
            management.setManagement_id((Integer) map.get("management_id"));
            management.setUser_id((Integer) map.get("user_id"));
            management.setSubject((String) map.get("subject"));
            management.setLink((String) map.get("link"));
            management.setStatus((Integer) map.get("status"));
            management.setCreate_at((LocalDateTime) map.get("create_at"));
            management.setUpdate_at((LocalDateTime) map.get("update_at"));
            management.setDelete_at((LocalDateTime) map.get("delete_at"));
            management.setCompletion_schedule((Date) map.get("completion_schedule"));
        } else {
            management.setManagement_id(-1);
            management.setDelete_at((LocalDateTime) LocalDateTime.now());
        }

        return management;
    }

    /**
     * 進捗データ更新
     * @param management 進捗データ
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int updateManagementData(Management management) throws DataAccessException {
        int rowNumber = jdbc.update("UPDATE management SET user_id = ?, subject = ?, link = ?, status = ?, update_at = CURRENT_TIMESTAMP, completion_schedule = ? WHERE management_id = ?",
                                    management.getUser_id(), management.getSubject(), management.getLink(), management.getStatus(), management.getCompletion_schedule(), management.getManagement_id());
        return rowNumber;
    }

    /**
     * IDによる進捗データ削除(delete_atの追加)
     * @param id 管理ID
     * @return 削除された行数
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int deleteManagementDataById(String id) throws DataAccessException {
        int rowNumber = jdbc.update("UPDATE management SET status = 5, update_at = CURRENT_TIMESTAMP, delete_at = CURRENT_TIMESTAMP WHERE management_id = ?", id);
        return rowNumber;
    }
}

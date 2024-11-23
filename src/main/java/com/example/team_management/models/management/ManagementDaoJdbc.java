package com.example.team_management.models.management;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("ManagementDaoJdbc")
public class ManagementDaoJdbc implements ManagementDao{

    @Autowired
    JdbcTemplate jdbc;


    @Override
    public int addManagementData(Management manaegment) throws DataAccessException {
        // データ追加
        int rowNumber = jdbc.update("INSERT INTO management(user_id, subject, link, status,completion_schedule) VALUES (?,?,?,?,?)", 
                                        manaegment.getUser_id(), manaegment.getSubject(), manaegment.getLink(), manaegment.getStatus(), manaegment.getCompletion_schedule());
        return rowNumber;
    }


    @Override
    public List<Management> getAllCompletionManagementData() throws DataAccessException {
        // 全完了データ取得
        List<Map<String, Object>> geList = jdbc.queryForList("SELECT * FROM management WHERE delete_at IS NOT null");

        // リターン用インスタンスを生成
        List<Management> managementList = new ArrayList<>();

        for (Map<String, Object> map : geList){

            // インスタンスの生成と初期化
            Management management = new Management();

            management.setManagement_id((Integer) map.get("management_id"));
            management.setUser_id((Integer) map.get("user_id"));
            management.setSubject((String) map.get("subject"));
            management.setLink((String) map.get("link"));
            management.setStatus((Integer) map.get("status"));
            management.setCreate_at((LocalDateTime) map.get("create_at"));
            management.setUpdate_at((LocalDateTime) map.get("update_at"));
            management.setDelete_at((LocalDateTime) map.get("delete_at"));
            management.setCompletion_schedule((LocalDate) map.get("completion_schedule"));

            // リターン用リストに追加
            managementList.add(management);
        }

        return managementList;
    }


    @Override
    public List<Management> getAllIncompleteManagementData() throws DataAccessException {
        // 全未完了データ取得
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM management WHERE delete_at IS null");

        // リターン用インスタンスを生成
        List<Management> managementList = new ArrayList<>();

        for (Map<String, Object> map : getList){

            // インスタンスの生成と初期化
            Management management = new Management();

            management.setManagement_id((Integer) map.get("management_id"));
            management.setUser_id((Integer) map.get("user_id"));
            management.setSubject((String) map.get("subject"));
            management.setLink((String) map.get("link"));
            management.setStatus((Integer) map.get("status"));
            management.setCreate_at((LocalDateTime) map.get("create_at"));
            management.setUpdate_at((LocalDateTime) map.get("update_at"));
            management.setDelete_at((LocalDateTime) map.get("delete_at"));
            management.setCompletion_schedule((LocalDate) map.get("completion_schedule"));

            // リターン用リストに追加
            managementList.add(management);
        }

        return managementList;
    }


    @Override
    public Management getManagementDataById(String id) throws DataAccessException {
        // idベースで1データを取得
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM management WHERE management_id = ?",id);

        // 取得出来たらそのデータ(Listの0)データがなければnull
        Map<String,Object> map = getList.isEmpty() ? null : getList.get(0);

        // リターン用インスタンス生成
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
            management.setCompletion_schedule((LocalDate) map.get("completion_schedule"));

        } else {

            // 存在しないIDの場合
            management.setManagement_id(-1);
            management.setDelete_at((LocalDateTime) LocalDateTime.now());

        }

        return management;
    }


    @Override
    public int updateManagementData(Management management) throws DataAccessException {
        // idベースで1データ更新
        int rowNumber = jdbc.update("UPDATE management SET user_id = ?, subject = ?, link = ?, status = ?, update_at = CURRENT_TIMESTAMP, completion_schedule = ? WHERE management_id = ?",
                                    management.getUser_id(), management.getSubject(), management.getLink(), management.getStatus(), management.getCompletion_schedule(), management.getManagement_id());
        return rowNumber;
    }


    @Override
    public int deleteManagementDataById(String id) throws DataAccessException {
        // delete_atを追加
        int rowNumber = jdbc.update("UPDATE management SET update_at = CURRENT_TIMESTAMP, delete_at = CURRENT_TIMESTAMP WHERE management_id = ?", id);
        return rowNumber;
    }
}


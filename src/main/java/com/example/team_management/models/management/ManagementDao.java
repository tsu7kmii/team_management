package com.example.team_management.models.management;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface ManagementDao {

    // データ追加
    public int addManagementData(Management manaegment) throws DataAccessException;

    // 全完了の年データ取得
    public List<Integer> getComplateYearData() throws DataAccessException;

    // 全年別完了データ取得
    public List<Management> getAllCompletionManagementData(String year) throws DataAccessException;

    // 全未完了データ取得
    public List<Management> getAllIncompleteManagementData() throws DataAccessException;
    
    // idベースで1データを取得
    public Management getManagementDataById(String id) throws DataAccessException;

    // idベースで1データ更新
    public int updateManagementData(Management management) throws DataAccessException;

    // delete_atを追加
    public int deleteManagementDataById(String id) throws DataAccessException;

}

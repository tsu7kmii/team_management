package com.example.team_management.models.management;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface ManagementDao {

    /**
     * 進捗データ追加
     * @param management 進捗データ
     * @return 追加された行数
     * @throws DataAccessException データアクセス例外
     */
    public int addManagementData(Management management) throws DataAccessException;

    /**
     * 完了年データ取得
     * @return 完了年のリスト
     * @throws DataAccessException データアクセス例外
     */
    public List<Integer> getComplateYearData() throws DataAccessException;

    /**
     * 年別完了データ取得
     * @param year 年
     * @return 完了データのリスト
     * @throws DataAccessException データアクセス例外
     */
    public List<Management> getAllCompletionManagementData(String year) throws DataAccessException;

    /**
     * 未完了データ取得
     * @return 未完了データのリスト
     * @throws DataAccessException データアクセス例外
     */
    public List<Management> getAllIncompleteManagementData() throws DataAccessException;
    
    /**
     * IDによる進捗データ取得
     * @param id 管理ID
     * @return 進捗データ
     * @throws DataAccessException データアクセス例外
     */
    public Management getManagementDataById(String id) throws DataAccessException;

    /**
     * 進捗データ更新
     * @param management 進捗データ
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    public int updateManagementData(Management management) throws DataAccessException;

    /**
     * IDによる進捗データ削除(delete_atの追加)
     * @param id 管理ID
     * @return 削除された行数
     * @throws DataAccessException データアクセス例外
     */
    public int deleteManagementDataById(String id) throws DataAccessException;

}

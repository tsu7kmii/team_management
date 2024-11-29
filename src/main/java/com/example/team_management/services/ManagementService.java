package com.example.team_management.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.exception.ErrorMessages;
import com.example.team_management.models.dao.ManagementDao;
import com.example.team_management.models.entity.Management;

/**
 * 管理サービスクラス
 */
@Transactional //トランザクション
@Service
public class ManagementService {

    @Autowired
    @Qualifier("ManagementDaoImpl")
    ManagementDao dao;

    /**
     * データ追加
     * @param userId ユーザーID
     * @param subject 科目
     * @param link 課題リンク
     * @param status ステータス
     * @param comDate 完了予定日
     * @throws Exception 例外
     */
    public void addManagementData(Integer userId, String subject, String link, Integer status, String comDate) throws Exception{

        // インスタンス作成
        Management management = new Management();  
        
        management.setUser_id(userId);
        management.setSubject(subject);
        management.setLink(link);
        management.setStatus(status);
        management.setCompletion_schedule(Date.valueOf(comDate));  

        if (dao.addManagementData(management) < 1)
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
    }

    /**
     * 進捗内容が編集可能(完了済ではないか)かチェック
     * @param id 管理ID
     * @return 編集可能な場合はManagementオブジェクト
     * @throws Exception 例外
     */
    public Management checkCanEdit(String id) throws Exception{

        Management management = getManagementDataById(id);
        
        if (management.getDelete_at() != null)
            throw new Exception(ErrorMessages.ManagementError.NOT_FOUND_ITEM);

        return management;
    }

    /**
     * 全完了の年データ取得
     * @return 年データのリスト
     */
    public List<Integer> getComplateYearData(){
        return dao.getComplateYearData();
    }

    /**
     * 全年別完了データ取得
     * @param year 年
     * @return 完了データのリスト
     */
    public List<Management> getAllCompletionManagementData(String year){
        return dao.getAllCompletionManagementData(year);
    }

    /**
     * 全未完了データ取得
     * @return 未完了データのリスト
     */
    public List<Management> getAllIncompleteManagementData(){
        return dao.getAllIncompleteManagementData();
    }

    /**
     * IDベースで1データを取得
     * @param id 管理ID
     * @return 進捗データ
     */
    public Management getManagementDataById(String id){
        return dao.getManagementDataById(id);
    }

    /**
     * IDベースで1データ更新
     * @param managementId 管理ID
     * @param userId ユーザーID
     * @param subject 科目
     * @param link 課題リンク
     * @param status ステータス
     * @param comDate 完了予定日
     * @throws Exception 例外
     */
    public void updateManagementData(Integer managementId, Integer userId, String subject, String link, Integer status, String comDate) throws Exception{

        // インスタンス作成
        Management management = new Management(); 

        management.setManagement_id(managementId); 
        management.setUser_id(userId);
        management.setSubject(subject);
        management.setLink(link);
        management.setStatus(status);
        management.setCompletion_schedule(Date.valueOf(comDate));

        if (dao.updateManagementData(management) < 1)
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
    }

    /**
     * IDベースでデータ削除(完了)
     * @param id 管理ID
     * @throws Exception 例外
     */
    public void deleteManagementDataById(String id) throws Exception{

        if (dao.deleteManagementDataById(id) < 1)
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
    }
}

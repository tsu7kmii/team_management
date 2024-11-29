
// Start of Selection
package com.example.team_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * @param manaegment 進捗データ
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean addManagementData(Management manaegment){

        int rowNumber = dao.addManagementData(manaegment);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
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
     * @param management 進捗データ
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean updateManagementData(Management management){

        int rowNumber = dao.updateManagementData(management);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    /**
     * IDベースでデータ削除
     * @param id 管理ID
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean deleteManagementDataById(String id){

        int rowNumber = dao.deleteManagementDataById(id);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
}

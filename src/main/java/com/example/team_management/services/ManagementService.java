package com.example.team_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.models.management.Management;
import com.example.team_management.models.management.ManagementDao;

@Transactional //トランザクション
@Service
public class ManagementService {

    @Autowired
    @Qualifier("ManagementDaoJdbc")
    ManagementDao dao;

    public boolean addManagementData(Management manaegment){

        // データ追加
        int rowNumber = dao.addManagementData(manaegment);

        boolean result = false;

        if (rowNumber > 0) {
            // 成功
            result = true;
        }
        return result;
    }

    public List<Integer> getComplateYearData(){
        // 全完了の年データ取得
        return dao.getComplateYearData();
    }


    public List<Management> getAllCompletionManagementData(String year){

        // 全年別完了データ取得
        return dao.getAllCompletionManagementData(year);
    }


    public List<Management> getAllIncompleteManagementData(){

        // 全未完了データ取得
        return dao.getAllIncompleteManagementData();
    }


    public Management getManagementDataById(String id){

        // idベースで1データを取得
        return dao.getManagementDataById(id);
    }


    public boolean updateManagementData(Management management){

        // idベースで1データ更新
        int rowNumber = dao.updateManagementData(management);

        boolean result = false;

        if (rowNumber > 0) {
            // 成功
            result = true;
        }
        return result;
    }


    public boolean deleteManagementDataById(String id){

        // delete_atを追加
        int rowNumber = dao.deleteManagementDataById(id);

        boolean result = false;

        if (rowNumber > 0) {
            // 成功
            result = true;
        }
        return result;
    }
}

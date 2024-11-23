package com.example.team_management.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.models.user.UserDao;

@Transactional // トランザクション
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoJdbc")
    UserDao dao;

    public Map<String, String> getUserNameList(){
        
        // 全ユーザー情報取得
        return dao.getUserNameList();
    }
}

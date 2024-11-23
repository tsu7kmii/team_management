package com.example.team_management.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.models.user.User;
import com.example.team_management.models.user.UserDao;

@Transactional // トランザクション
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoJdbc")
    UserDao dao;

    public Map<Integer, String> getUserNameList(){
        
        // 全ユーザー情報取得
        return dao.getUserNameList();
    }

    public boolean newUserRegister(User user){

        int rowNumber = dao.newUserRegister(user);

        boolean result = false;

        if (rowNumber > 0) {
            // 成功
            result = true;
        }
        return result; 
    }

    public LocalDateTime localDateTimeFormatter(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime accessExpiresAt = LocalDateTime.parse(date, formatter);
        return accessExpiresAt;
    }
}

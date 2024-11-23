package com.example.team_management.models.user;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface UserDao {
    
    // 全ユーザー情報取得
    public Map<Integer, String> getUserNameList() throws DataAccessException;

    // 新規登録
    public int newUserRegister(User user) throws DataAccessException;
}

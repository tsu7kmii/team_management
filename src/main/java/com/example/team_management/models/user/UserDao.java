package com.example.team_management.models.user;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface UserDao {
    
    // 全ユーザー情報取得
    public Map<String, String> getUserNameList() throws DataAccessException;
}

package com.example.team_management.models.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("UserDaoJdbc")
public class UserDaoJdbc implements UserDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Map<Integer, String> getUserNameList() throws DataAccessException {
        // 全ユーザー情報取得
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_table");

        HashMap<Integer, String> userMap = new HashMap<>();

        for (Map<String, Object> map : getList){
            userMap.put((Integer) map.get("user_id"),(String) map.get("user_name"));
        }

        return userMap;
    }

    @Override
    public int newUserRegister(User user) throws DataAccessException {
        // 新規登録
        int rowNumber = jdbc.update("INSERT INTO user_table(user_name, access_token, refresh_token, access_expires_at, refresh_expires_at, password, password_salt, email, permission_level ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
                                    , user.getUser_name(), user.getAccess_token(), user.getRefresh_token(), user.getAccess_expires_at(), user.getRefresh_expires_at(), user.getPassword(), user.getPassword_salt(), user.getEmail(), user.getPermission_level());

        return rowNumber;
    }

}

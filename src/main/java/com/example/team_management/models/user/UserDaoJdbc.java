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
    public Map<String, String> getUserNameList() throws DataAccessException {
        // 全ユーザー情報取得
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_table");

        HashMap<String, String> userMap = new HashMap<>();

        for (Map<String, Object> map : getList){
            userMap.put((String) map.get("user_id"),(String) map.get("user_name"));
        }

        return userMap;
    }

}

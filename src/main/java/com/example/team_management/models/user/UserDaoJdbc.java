package com.example.team_management.models.user;

import java.time.LocalDateTime;
import java.util.*;

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
        int rowNumber = jdbc.update("INSERT INTO user_table(user_name, access_token, refresh_token, access_expires_at, refresh_expires_at, password, email, permission_level ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
                                    , user.getUser_name(), user.getAccess_token(), user.getRefresh_token(), user.getAccess_expires_at(), user.getRefresh_expires_at(), user.getPassword(), user.getEmail(), user.getPermission_level());

        return rowNumber;
    }

    @Override
    public Optional<User> findByEmail(String email) throws DataAccessException {
        // ログイン用
        List<Map<String,Object>> list = jdbc.queryForList("SELECT * FROM user_table WHERE email = ?", email);

        // 取得出来たらそのデータ(Listの0)データがなければnull
        if (list.isEmpty()) {
            return Optional.empty();
        }

        Map<String,Object> map = list.get(0);
        // return用の変数
        User user = new User();

        user.setUser_id((Integer) map.get("user_id"));
        user.setUser_name((String) map.get("user_name"));
        user.setAccess_token((String) map.get("access_token"));
        user.setRefresh_token((String) map.get("refresh_token"));
        user.setAccess_expires_at((LocalDateTime) map.get("access_expires_at"));
        user.setRefresh_expires_at((LocalDateTime) map.get("refresh_expires_at"));
        user.setPassword((String) map.get("password"));
        user.setEmail((String) map.get("email"));
        user.setPermission_level((Integer) map.get("permission_level"));
        user.setCreate_at((LocalDateTime) map.get("create_at"));
        user.setUpdate_at((LocalDateTime) map.get("update_at"));
        user.setDelete_at((LocalDateTime) map.get("delete_at"));

        return Optional.of(user);
    }

    @Override
    public boolean isEmailAlreadyRegistered(String email) throws DataAccessException {

        // アカウント作成時、既に使用されているメールアドレスかどうか
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT email FROM user_table WHERE email = ? ",email);

        // メールアドレスが既に登録されている場合はtrueを返す
        return !getList.isEmpty();
    }

}

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
        // 全<id:name>セットユーザー情報取得
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
        int rowNumber = jdbc.update("INSERT INTO user_table(user_name, password, email, permission_level ) VALUES (?, ?, ?, ?)"
                                    , user.getUser_name(), user.getPassword(), user.getEmail(), user.getPermission_level());

        return rowNumber;
    }

    @Override
    public Optional<User> findByEmail(String email) throws DataAccessException {
        // ログイン用
        List<Map<String,Object>> list = jdbc.queryForList("SELECT * FROM user_table WHERE email = ? AND delete_at IS NULL", email);

        // 取得出来たらそのデータ(Listの0)データがなければnull
        if (list.isEmpty()) {
            return Optional.empty();
        }

        Map<String,Object> map = list.get(0);
        // return用の変数
        User user = new User();

        user.setUser_id((Integer) map.get("user_id"));
        user.setUser_name((String) map.get("user_name"));
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

    @Override
    public int findIdByEmail(String email) throws DataAccessException {
        // ログインアカウント標準選択用
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT user_id FROM user_table WHERE email = ?",email);

        // 初期値をDBのオートインクリメントでとらない数値にしている
        int getId = -1;

        for (Map<String, Object> map : getList){
            getId = (Integer) map.get("user_id");
        }

        return getId;
    }

    @Override
    public int changePasswordByEmail(String email, String newPassword) {
        // パスワード変更
        int rowNumber = jdbc.update("UPDATE user_table SET password = ?, update_at = CURRENT_TIMESTAMP WHERE email = ?", newPassword, email);
        return rowNumber;
    }

    @Override
    public int changeNameByEmail(String email, String newName) throws DataAccessException {
        // 名前変更
        int rowNumber = jdbc.update("UPDATE user_table SET user_name = ?, update_at = CURRENT_TIMESTAMP WHERE email = ?", newName, email);
        return rowNumber;
    }

    @Override
    public List<User> getAllUserList() throws DataAccessException {
        // ユーザー一覧表示用ユーザー情報取得
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_table WHERE delete_at IS NULL ");

        // 返却用リスト
        List<User> userList = new ArrayList<>();

        for (Map<String, Object> map : getList){

            // インスタンス生成と初期化
            User user = new User();

            user.setUser_id((Integer) map.get("user_id"));
            user.setUser_name((String) map.get("user_name"));
            user.setEmail((String) map.get("email"));
            user.setPermission_level((Integer) map.get("permission_level"));

            // 返却用のリストに追加
            userList.add(user);
        }

        return userList;
    }

    @Override
    public int changeUserPermissionLevelById(Integer id, Integer level) throws DataAccessException {
        // ユーザー権限変更
        int rowNumber = jdbc.update("UPDATE user_table SET permission_level = ?, update_at = CURRENT_TIMESTAMP WHERE user_id = ?", level,id);

        return rowNumber;
    }

    @Override
    public int deleteUserById(Integer id) throws DataAccessException {
        // ユーザー削除
        int rowNumber = jdbc.update("UPDATE user_table SET delete_at = CURRENT_TIMESTAMP WHERE user_id = ?", id);

        return rowNumber;
    }
}

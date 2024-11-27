package com.example.team_management.models.user;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * UserDaoJdbcクラス
 * ユーザー情報のデータベース操作を行うクラス
 */
@Repository("UserDaoJdbc")
public class UserDaoJdbc implements UserDao{

    @Autowired
    JdbcTemplate jdbc;

    /**
     * ユーザー名リスト取得
     * @return ユーザーIDと名前のマップ
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public Map<Integer, String> getUserNameList() throws DataAccessException {
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_table");

        HashMap<Integer, String> userMap = new HashMap<>();

        for (Map<String, Object> map : getList){
            userMap.put((Integer) map.get("user_id"),(String) map.get("user_name"));
        }

        return userMap;
    }

    /**
     * 新規ユーザー登録
     * @param user ユーザー情報
     * @return 追加された行数
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int newUserRegister(User user) throws DataAccessException {
        int rowNumber = jdbc.update("INSERT INTO user_table(user_name, password, email, permission_level ) VALUES (?, ?, ?, ?)"
                                    , user.getUser_name(), user.getPassword(), user.getEmail(), user.getPermission_level());

        return rowNumber;
    }

    /**
     * メールアドレスでユーザー検索
     * @param email メールアドレス
     * @return ユーザー情報のオプショナル
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public Optional<User> findByEmail(String email) throws DataAccessException {
        List<Map<String,Object>> list = jdbc.queryForList("SELECT * FROM user_table WHERE email = ? AND delete_at IS NULL", email);

        if (list.isEmpty()) {
            return Optional.empty();
        }

        Map<String,Object> map = list.get(0);
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

    /**
     * メールアドレスの登録確認
     * @param email メールアドレス
     * @return 登録済みかどうかの真偽値
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public boolean isEmailAlreadyRegistered(String email) throws DataAccessException {
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT email FROM user_table WHERE email = ? ",email);

        return !getList.isEmpty();
    }

    /**
     * メールアドレスでユーザーID検索
     * @param email メールアドレス
     * @return ユーザーID
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int findIdByEmail(String email) throws DataAccessException {
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT user_id FROM user_table WHERE email = ?",email);

        int getId = -1;

        for (Map<String, Object> map : getList){
            getId = (Integer) map.get("user_id");
        }

        return getId;
    }

    /**
     * メールアドレスでパスワード変更
     * @param email メールアドレス
     * @param newPassword 新しいパスワード
     * @return 更新された行数
     */
    @Override
    public int changePasswordByEmail(String email, String newPassword) {
        int rowNumber = jdbc.update("UPDATE user_table SET password = ?, update_at = CURRENT_TIMESTAMP WHERE email = ?", newPassword, email);
        return rowNumber;
    }

    /**
     * メールアドレスで名前変更
     * @param email メールアドレス
     * @param newName 新しい名前
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int changeNameByEmail(String email, String newName) throws DataAccessException {
        int rowNumber = jdbc.update("UPDATE user_table SET user_name = ?, update_at = CURRENT_TIMESTAMP WHERE email = ?", newName, email);
        return rowNumber;
    }

    /**
     * 全ユーザーリスト取得
     * @return ユーザー情報のリスト
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public List<User> getAllUserList() throws DataAccessException {
        List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user_table WHERE delete_at IS NULL ");

        List<User> userList = new ArrayList<>();

        for (Map<String, Object> map : getList){
            User user = new User();

            user.setUser_id((Integer) map.get("user_id"));
            user.setUser_name((String) map.get("user_name"));
            user.setEmail((String) map.get("email"));
            user.setPermission_level((Integer) map.get("permission_level"));

            userList.add(user);
        }

        return userList;
    }

    /**
     * ユーザーIDで権限レベル変更
     * @param id ユーザーID
     * @param level 新しい権限レベル
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int changeUserPermissionLevelById(Integer id, Integer level) throws DataAccessException {
        int rowNumber = jdbc.update("UPDATE user_table SET permission_level = ?, update_at = CURRENT_TIMESTAMP WHERE user_id = ?", level,id);

        return rowNumber;
    }

    /**
     * ユーザーIDでユーザー削除(delete_atの追加)
     * @param id ユーザーID
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    @Override
    public int deleteUserById(Integer id) throws DataAccessException {
        int rowNumber = jdbc.update("UPDATE user_table SET delete_at = CURRENT_TIMESTAMP WHERE user_id = ?", id);

        return rowNumber;
    }
}

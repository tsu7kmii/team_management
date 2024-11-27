package com.example.team_management.models.user;

import java.util.*;

import org.springframework.dao.DataAccessException;

public interface UserDao {
    
    /**
     * ユーザー名リスト取得
     * @return ユーザーIDと名前のマップ
     * @throws DataAccessException データアクセス例外
     */
    public Map<Integer, String> getUserNameList() throws DataAccessException;

    /**
     * 新規ユーザー登録
     * @param user ユーザー情報
     * @return 追加された行数
     * @throws DataAccessException データアクセス例外
     */
    public int newUserRegister(User user) throws DataAccessException;

    /**
     * メールアドレスでユーザー検索
     * @param email メールアドレス
     * @return ユーザー情報のオプショナル
     * @throws DataAccessException データアクセス例外
     */
    public Optional<User> findByEmail(String email) throws DataAccessException;

    /**
     * メールアドレスの登録確認
     * @param email メールアドレス
     * @return 登録済みかどうかの真偽値
     * @throws DataAccessException データアクセス例外
     */
    public boolean isEmailAlreadyRegistered(String email) throws DataAccessException;

    /**
     * メールアドレスでユーザーID検索
     * @param email メールアドレス
     * @return ユーザーID
     * @throws DataAccessException データアクセス例外
     */
    public int findIdByEmail(String email) throws DataAccessException;

    /**
     * メールアドレスでパスワード変更
     * @param email メールアドレス
     * @param newPassword 新しいパスワード
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    public int changePasswordByEmail(String email, String newPassword) throws DataAccessException;

    /**
     * メールアドレスで名前変更
     * @param email メールアドレス
     * @param newName 新しい名前
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    public int changeNameByEmail(String email, String newName) throws DataAccessException;

    /**
     * 全ユーザーリスト取得
     * @return ユーザー情報のリスト
     * @throws DataAccessException データアクセス例外
     */
    public List<User> getAllUserList() throws DataAccessException;

    /**
     * ユーザーIDで権限レベル変更
     * @param id ユーザーID
     * @param level 新しい権限レベル
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    public int changeUserPermissionLevelById(Integer id, Integer level) throws DataAccessException;
    
    /**
     * ユーザーIDでユーザー削除
     * @param id ユーザーID
     * @return 更新された行数
     * @throws DataAccessException データアクセス例外
     */
    public int deleteUserById(Integer id) throws DataAccessException;
}

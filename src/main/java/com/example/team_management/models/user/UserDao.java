package com.example.team_management.models.user;

import java.util.*;

import org.springframework.dao.DataAccessException;

public interface UserDao {
    
    // 全<id:name>セットユーザー情報取得
    public Map<Integer, String> getUserNameList() throws DataAccessException;

    // 新規登録
    public int newUserRegister(User user) throws DataAccessException;

    // ログイン用
    public Optional<User> findByEmail(String email) throws DataAccessException;

    // アカウント作成時、既に使用されているメールアドレスかどうか
    public boolean isEmailAlreadyRegistered(String email) throws DataAccessException;

    // ログインアカウント標準選択用
    public int findIdByEmail(String email) throws DataAccessException;

    // パスワード変更
    public int changePasswordByEmail(String email,String newPassword) throws DataAccessException;

    // 名前変更
    public int changeNameByEmail(String email, String newName) throws DataAccessException;

    // ユーザー一覧表示用ユーザー情報取得
    public List<User> getAllUserList() throws DataAccessException;

    // ユーザー権限変更
    public int changeUserPermissionLevelById(Integer id, Integer level) throws DataAccessException;
    
    // ユーザー削除
    public int deleteUserById(Integer id) throws DataAccessException;
}

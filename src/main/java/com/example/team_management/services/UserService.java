package com.example.team_management.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.models.dao.UserDao;
import com.example.team_management.models.entity.User;

@Transactional // トランザクション
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoImpl")
    UserDao dao;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 全<id:name>セットユーザー情報取得
     * @return ユーザー情報のマップ
     */
    public Map<Integer, String> getUserNameList(){
        return dao.getUserNameList();
    }

    /**
     * 新規登録
     * @param user ユーザー情報
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean newUserRegister(User user){
        int rowNumber = dao.newUserRegister(user);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result; 
    }

    /**
     * mysql -> 表示用にフォーマットする
     * @param date 日付文字列
     * @return フォーマットされたLocalDateTime
     */
    public LocalDateTime localDateTimeFormatter(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime accessExpiresAt = LocalDateTime.parse(date, formatter);
        return accessExpiresAt;
    }

    /**
     * ユーザー登録時のパスワードのハッシュ化
     * @param password パスワード
     * @return ハッシュ化されたパスワード
     */
    public String createHash(String password){
        return passwordEncoder.encode(password);
    }

    /**
     * ログイン時のパスワード認証
     * @param inputPassword 入力されたパスワード
     * @param hashPassword ハッシュ化されたパスワード
     * @return 一致する場合はtrue、一致しない場合はfalse
     */
    public boolean matchHashPassword(String inputPassword, String hashPassword){
        return passwordEncoder.matches(inputPassword, hashPassword);
    }

    /**
     * アカウント作成時、既に使用されているメールアドレスかどうか
     * @param email メールアドレス
     * @return 既に登録されている場合はtrue、登録されていない場合はfalse
     */
    public boolean isEmailAlreadyRegistered(String email){
        return dao.isEmailAlreadyRegistered(email);
    }

    /**
     * emailからidを取得
     * @param email メールアドレス
     * @return ユーザーID
     */
    public int findIdByEmail(String email){
        return dao.findIdByEmail(email);
    }

    /**
     * パスワード変更
     * @param email メールアドレス
     * @param newPassword 新しいパスワード
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean changePasswordByEmail(String email, String newPassword){
        int rowNumber = dao.changePasswordByEmail(email, newPassword);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 名前変更
     * @param email メールアドレス
     * @param newName 新しい名前
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean changeNameByEmail(String email, String newName){
        int rowNumber = dao.changeNameByEmail(email, newName);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    /**
     * ユーザー一覧表示
     * @return ユーザーのリスト
     */
    public List<User> getAllUserList(){
        return dao.getAllUserList();
    }

    /**
     * ユーザー権限変更
     * @param id ユーザーID
     * @param level 権限レベル
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean changeUserPermissionLevelById(Integer id, Integer level){
        int rowNumber = dao.changeUserPermissionLevelById(id, level);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    /**
     * ユーザー削除
     * @param id ユーザーID
     * @return 成功した場合はtrue、失敗した場合はfalse
     */
    public boolean deleteUserById(Integer id){
        int rowNumber = dao.deleteUserById(id);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
}

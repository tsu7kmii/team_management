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

import com.example.team_management.exception.ErrorMessages;
import com.example.team_management.models.dao.UserDao;
import com.example.team_management.models.entity.User;
import com.example.team_management.requests.SignupRequest;

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
     * @param signupRequest ユーザー情報
     * @throws Exception 例外
     */
    public void newUserRegister(SignupRequest signupRequest) throws Exception{

        if (isEmailAlreadyRegistered(signupRequest.getEmail())) {
            // 既にemailが使用済の場合
            throw new Exception(ErrorMessages.UserErros.ALREEADY_USED_EMAIL);
        }

        // 登録用インスタンス作成
        User user = new User();

        user.setUser_name(signupRequest.getName());
        user.setPassword(createHash(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPermission_level(2);

        if (dao.newUserRegister(user) < 1) 
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
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
     * @throws Exception 例外
     */
    public void changePasswordByEmail(String email, String newPassword) throws Exception{

        if (dao.changePasswordByEmail(email, createHash(newPassword)) < 1)
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
    }

    /**
     * 名前変更
     * @param email メールアドレス
     * @param newName 新しい名前
     * @throws Exception 例外
     */
    public void changeNameByEmail(String email, String newName) throws Exception{

        if (dao.changeNameByEmail(email, newName) < 1)
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
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
     * @throws Exception 例外
     */
    public void changeUserPermissionLevelById(Integer id, Integer level) throws Exception{
        
        if (dao.changeUserPermissionLevelById(id, level) < 1)
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
    }

    /**
     * ユーザー削除
     * @param id ユーザーID
     * @throws Exception 例外
     */
    public void deleteUserById(Integer id) throws Exception{
        
        if (dao.deleteUserById(id) < 1)
            throw new Exception(ErrorMessages.GlobalErrors.SQL_ERROR);
    }
}

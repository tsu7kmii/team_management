package com.example.team_management.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.models.user.User;
import com.example.team_management.models.user.UserDao;

@Transactional // トランザクション
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoJdbc")
    UserDao dao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Map<Integer, String> getUserNameList(){
        
        // 全ユーザー情報取得
        return dao.getUserNameList();
    }

    public boolean newUserRegister(User user){

        int rowNumber = dao.newUserRegister(user);

        boolean result = false;

        if (rowNumber > 0) {
            // 成功
            result = true;
        }
        return result; 
    }

    // mysql -> 表示用にフォーマットする
    public LocalDateTime localDateTimeFormatter(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime accessExpiresAt = LocalDateTime.parse(date, formatter);
        return accessExpiresAt;
    }

    // ユーザー登録時のパスワードのハッシュ化
    public String createHash(String password){
        return passwordEncoder.encode(password);
    }

    // ログイン時のパスワード認証
    public boolean matchHashPassword(String inputPassword, String hashPassword){
        return passwordEncoder.matches(inputPassword, hashPassword);
    }

    // アカウント作成時、既に使用されているメールアドレスかどうか
    // メールアドレスが既に登録されている場合はtrueを返す
    public boolean isEmailAlreadyRegistered(String email){
        return dao.isEmailAlreadyRegistered(email);
    }
}

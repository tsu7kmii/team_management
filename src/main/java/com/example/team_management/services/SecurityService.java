package com.example.team_management.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.team_management.models.dao.PasswordTokenRepository;
import com.example.team_management.models.entity.PasswordResetToken;

@Service
public class SecurityService {

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    /**
     * パスワードリセットトークンの検証
     * 
     * @param token トークン
     * @return 検証結果
     */
    public String validatePasswordResetToken(String token){

        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "不正なトークンです"
                : isTokenExpired(passToken) ? "有効期限切れのトークンです"
                : null;
    }

    /**
     * トークンの存在確認
     * 
     * @param passToken パスワードリセットトークン
     * @return 存在するかどうか
     */
    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    /**
     * トークンの有効期限確認
     * 
     * @param passToken パスワードリセットトークン
     * @return 有効期限切れかどうか
     */
    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}

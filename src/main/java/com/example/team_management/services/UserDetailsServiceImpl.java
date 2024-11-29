package com.example.team_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.models.dao.UserDao;
import com.example.team_management.models.entity.User;

@Component
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    UserDao dao;

    /**
     * ユーザー名でユーザーをロードするメソッド
     * @param email ユーザーのメールアドレス
     * @return UserDetails ユーザーの詳細情報
     * @throws UsernameNotFoundException ユーザーが見つからない場合にスローされる例外
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = dao.findByEmail(email)
                    .orElseThrow(() -> {
                        System.out.println("ユーザー:" + email + "が見つかりません。");
                        throw new UsernameNotFoundException(email);
                    });

        return org.springframework.security.core.userdetails.User.withUsername(
            user.getEmail())
            .password(user.getPassword())
            .roles(mapRole(user.getPermission_level()))
            .build();
    }

    /**
     * ユーザーの権限レベルをロールにマッピングするメソッド
     * @param role ユーザーの権限レベル
     * @return String マッピングされたロール
     */
    private String mapRole(int role){
        if (role == 1){
            return "ADMIN";
        } else if (role == 2) {
            return "USER";
        } else {
            return "OTHER";
        }
    }
}

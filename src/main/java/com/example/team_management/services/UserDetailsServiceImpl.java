package com.example.team_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.team_management.models.user.User;
import com.example.team_management.models.user.UserDao;

@Component
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DBからユーザー情報をSELECTする。
		// ユーザーが見つからない場合は例外をスローする
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

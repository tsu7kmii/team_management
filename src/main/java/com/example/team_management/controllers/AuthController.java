package com.example.team_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team_management.models.user.User;
import com.example.team_management.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {
    
    @Autowired
    UserService userService;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String newUserRegister(@RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password){
        // 登録処理
        // 登録用インスタンス作成
        User user = new User();
    
        user.setUser_name(name);
        user.setAccess_token("NoSetting");
        user.setRefresh_token("NotSetting");
        
        user.setAccess_expires_at(userService.localDateTimeFormatter("2024-09-24 23:51:08"));
        user.setRefresh_expires_at(userService.localDateTimeFormatter("2024-09-24 23:51:08"));

        user.setPassword(userService.createHash(password));
        
        user.setEmail(email);
        user.setPermission_level(2);


        boolean result = userService.newUserRegister(user);

        if (result == true){
            // 成功
            return "redirect:/";
        } else {
            // 失敗
            return "redirect:/management";
        }                  
    }

    @RequestMapping("/login")
    public String login(){

        // ログイン
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
        
        // ログアウト
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login";
    }

    @RequestMapping("/access-denied")
    public String accessDenied() {
		return "access-denied";
	}
}

package com.example.team_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.team_management.models.user.SignupValidation;
import com.example.team_management.models.user.User;
import com.example.team_management.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {
    
    @Autowired
    UserService userService;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @RequestMapping(value = "/user_register", method = RequestMethod.POST)
    public String newUserRegister(@Validated SignupValidation signupValidation, BindingResult result, Model model){

        if (result.hasErrors()){
            return "auth/sigin_in";
        }

        // 登録処理
        // 登録用インスタンス作成
        User user = new User();
    
        user.setUser_name(signupValidation.getName());

        // 今後実装または放置
        user.setAccess_token("NoSetting");
        user.setRefresh_token("NotSetting");
        user.setAccess_expires_at(userService.localDateTimeFormatter("2024-09-24 23:51:08"));
        user.setRefresh_expires_at(userService.localDateTimeFormatter("2024-09-24 23:51:08"));


        user.setPassword(userService.createHash(signupValidation.getPassword()));

        if (userService.isEmailAlreadyRegistered(signupValidation.getEmail())) {
            // 既にemailが使用済の場合
            return "redirect:/error/used_email_error?parms=" + signupValidation.getEmail();
        }

        user.setEmail(signupValidation.getEmail());
        user.setPermission_level(2);


        boolean createResults = userService.newUserRegister(user);

        if (createResults == true){
            // 成功
            return "redirect:/";
        } else {
            // 失敗
            return "redirect:/error/create_account_error";
        }                  
    }

    @RequestMapping("/login")
    public String login(){

        // ログイン
        return "auth/login";
    }

    @RequestMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
        
        // ログアウト
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login";
    }

    @RequestMapping("/sign_in")
    public String register(SignupValidation signupValidation){

        // アカウント作成
        return "auth/sigin_in";
    }

    @RequestMapping("/access-denied")
    public String accessDenied() {
		return "auth/access-denied";
	}
}

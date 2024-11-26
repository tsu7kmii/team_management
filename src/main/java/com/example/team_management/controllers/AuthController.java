package com.example.team_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.team_management.models.user.NameChangeValidation;
import com.example.team_management.models.user.PasswordChangeValidation;
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
    public String newUserRegister(@Validated SignupValidation signupValidation, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            // バリデーションチェック
            return "auth/sign_up";
        }

        if (userService.isEmailAlreadyRegistered(signupValidation.getEmail())) {
            // 既にemailが使用済の場合
            return "redirect:/error/used_email_error?param=" + signupValidation.getEmail();
        }

        // 登録処理
        // 登録用インスタンス作成
        User user = new User();

        user.setUser_name(signupValidation.getName());
        user.setPassword(userService.createHash(signupValidation.getPassword()));
        user.setEmail(signupValidation.getEmail());
        user.setPermission_level(2);
        
        // 実行
        boolean isRegisterResult = userService.newUserRegister(user);

        if (isRegisterResult == true){
            // 成功
            return "redirect:/";
        } else {
            // 失敗
            return "redirect:/error/create_account_error";
        }                  
    }

    @RequestMapping(value = "/user_change_password", method=RequestMethod.POST)
    public String changePasswordRegister(@Validated PasswordChangeValidation passwordChangeValidation,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal UserDetails userDetails){
        
        
        if (bindingResult.hasErrors()) {
            // バリデーションチェック
            return "auth/change_password";
        }
        
        if (!passwordChangeValidation.getNewPassword().equals(passwordChangeValidation.getAgainNewPassword())) {
            // 入力された2種類のパスワードが一致しない場合
            return "redirect:/error/not_equal_password_error";
        }

        // パスワード変更
        boolean isChangePasswordResult = userService.changePasswordByEmail(userDetails.getUsername(),userService.createHash(passwordChangeValidation.getNewPassword()));

        if (isChangePasswordResult){
            // 成功時ログアウト
            return "redirect:/logout";
        } else {
            return "redirect:/error/not_change_password_error";
        }
    }

    @RequestMapping(value = "/user_change_name", method=RequestMethod.POST)
    public String changeNameRegister(@Validated NameChangeValidation nameChangeValidation,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal UserDetails userDetails){
        
        
        if (bindingResult.hasErrors()) {
            // バリデーションチェック
            return "auth/change_name";
        }
        
        // 名前変更
        boolean isChangeNameResult = userService.changeNameByEmail(userDetails.getUsername(),nameChangeValidation.getNewName());

        if (isChangeNameResult){
            // 成功時ログアウト
            return "redirect:/";
        } else {
            return "redirect:/error/not_change_name_error";
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
        return "redirect:/login?logout";
    }

    @RequestMapping("/sign_up")
    public String viewRegister(SignupValidation signupValidation){

        // アカウント作成
        return "auth/sign_up";
    }

    @RequestMapping("/change_password")
    public String viewChangePassword(PasswordChangeValidation passwordChangeValidation){

        // パスワード変更
        return "auth/change_password";
    }

    @RequestMapping("/change_name")
    public String viewChangeName(NameChangeValidation nameChangeValidation){

        // パスワード変更
        return "auth/change_name";
    }

    @RequestMapping("/access-denied")
    public String accessDenied() {

        // 権限エラー
		return "auth/access-denied";
	}
}

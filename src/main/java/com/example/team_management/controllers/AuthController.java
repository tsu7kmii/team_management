package com.example.team_management.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team_management.models.entity.User;
import com.example.team_management.requests.NameChangeRequest;
import com.example.team_management.requests.PasswordChangeRequest;
import com.example.team_management.requests.SignupRequest;
import com.example.team_management.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 認証コントローラー
 */
@Controller
public class AuthController {
    
    @Autowired
    UserService userService;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    /**
     * 新規ユーザー登録処理
     * 
     * @param signupRequest サインアップバリデーション
     * @param bindingResult バインディング結果
     * @param model モデル
     * @return リダイレクト先
     */
    @RequestMapping(value = "/user_register", method = RequestMethod.POST)
    public String newUserRegister(@Validated SignupRequest signupRequest, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            // バリデーションチェック
            return "auth/sign_up";
        }
        
        // 実行
        try {
            userService.newUserRegister(signupRequest);
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/";            
    }

    /**
     * パスワード変更処理
     * 
     * @param passwordChangeRequest パスワード変更バリデーション
     * @param bindingResult バインディング結果
     * @param userDetails ユーザーディテール
     * @return リダイレクト先
     */
    @RequestMapping(value = "/user_change_password", method=RequestMethod.POST)
    public String changePasswordRegister(@Validated PasswordChangeRequest passwordChangeRequest,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model){
        
        
        if (bindingResult.hasErrors()) {
            if (!passwordChangeRequest.isPasswordValid()){
                model.addAttribute("passwordMismatchError", "パスワードが一致しません");
            }

            // バリデーションチェック
            return "auth/change_password";
        }

        // 実行
        try {
            userService.changePasswordByEmail(userDetails.getUsername(),passwordChangeRequest.getNewPassword());
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/logout";
        
    }

    /**
     * 名前変更処理
     * 
     * @param nameChangeRequest 名前変更バリデーション
     * @param bindingResult バインディング結果
     * @param userDetails ユーザーディテール
     * @return リダイレクト先
     */
    @RequestMapping(value = "/user_change_name", method=RequestMethod.POST)
    public String changeNameRegister(@Validated NameChangeRequest nameChangeRequest,
                                    BindingResult bindingResult,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model){
        
        
        if (bindingResult.hasErrors()) {
            // バリデーションチェック
            return "auth/change_name";
        }

        // 実行
        try {
            userService.changeNameByEmail(userDetails.getUsername(),nameChangeRequest.getNewName());
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/";
    }

    /**
     * 権限変更処理
     * 
     * @param userId ユーザーID
     * @param requestRole リクエストロール
     * @return リダイレクト先
     */
    @RequestMapping("/admin/change_role")
    public String changeRoleRegister(@RequestParam("user_id") Integer userId,
                                        @RequestParam("request_role") Integer requestRole,
                                        Model model){
        
        // 実行
        try {
            userService.changeUserPermissionLevelById(userId, requestRole); 
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/admin/user_list";
    }

    /**
     * ユーザー削除処理
     * 
     * @param userId ユーザーID
     * @return リダイレクト先
     */
    @RequestMapping("/admin/delete_user")
    public String deleteUserRegister(@RequestParam("user_id") Integer userId, Model model){

        // 実行
        try {
            userService.deleteUserById(userId);
        } catch (Exception e) {
            // エラーハンドリング
            model.addAttribute("error_message", e.getMessage());
            return "error/error";
        }

        return "redirect:/admin/user_list";
    }
    
    /**
     * ログイン画面表示
     * 
     * @return ログイン画面
     */
    @RequestMapping("/login")
    public String login(){

        return "auth/login";
    }

    /**
     * ログアウト処理
     * 
     * @param authentication 認証情報
     * @param request リクエスト
     * @param response レスポンス
     * @return リダイレクト先
     */
    @RequestMapping("/logout")
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response){
        
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login?logout";
    }

    /**
     * 新規アカウント登録画面表示
     * 
     * @param signupRequest アカウント作成バリデーション
     * @return 新規アカウント登録画面
     */
    @RequestMapping("/sign_up")
    public String viewRegister(SignupRequest signupRequest){

        return "auth/sign_up";
    }

    /**
     * パスワード変更画面表示
     * 
     * @param passwordChangeRequest パスワード変更バリデーション
     * @return パスワード変更画面
     */
    @RequestMapping("/change_password")
    public String viewChangePassword(PasswordChangeRequest passwordChangeRequest){

        return "auth/change_password";
    }

    /**
     * 名前変更画面表示
     * 
     * @param nameChangeRequest 名前変更バリデーション
     * @return 名前変更画面
     */
    @RequestMapping("/change_name")
    public String viewChangeName(NameChangeRequest nameChangeRequest){

        return "auth/change_name";
    }

    /**
     * ユーザー管理画面表示
     * 
     * @param model モデル
     * @return ユーザー管理画面
     */
    @RequestMapping("/admin/user_list")
    public String viewUserList(Model model){
        
        List<User> userList = userService.getAllUserList();

        model.addAttribute("users", userList);

        return "auth/user_list";
    }
}

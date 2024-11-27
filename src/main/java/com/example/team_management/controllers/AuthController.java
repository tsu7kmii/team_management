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

import com.example.team_management.models.user.NameChangeValidation;
import com.example.team_management.models.user.PasswordChangeValidation;
import com.example.team_management.models.user.SignupValidation;
import com.example.team_management.models.user.User;
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
     * @param signupValidation サインアップバリデーション
     * @param bindingResult バインディング結果
     * @param model モデル
     * @return リダイレクト先
     */
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

    /**
     * パスワード変更処理
     * 
     * @param passwordChangeValidation パスワード変更バリデーション
     * @param bindingResult バインディング結果
     * @param userDetails ユーザーディテール
     * @return リダイレクト先
     */
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

    /**
     * 名前変更処理
     * 
     * @param nameChangeValidation 名前変更バリデーション
     * @param bindingResult バインディング結果
     * @param userDetails ユーザーディテール
     * @return リダイレクト先
     */
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

    /**
     * 権限変更処理
     * 
     * @param userId ユーザーID
     * @param requestRole リクエストロール
     * @return リダイレクト先
     */
    @RequestMapping("/admin/change_role")
    public String changeRoleRegister(@RequestParam("user_id") Integer userId,
                                        @RequestParam("request_role") Integer requestRole){
        
        // 権限変更
        boolean isChangeRoleResult = userService.changeUserPermissionLevelById(userId, requestRole); 
        
        if (isChangeRoleResult){
            // 成功
            return "redirect:/admin/user_list";
        } else {
            return "redirect:/error/not_change_role_error";
        }
    }

    /**
     * ユーザー削除処理
     * 
     * @param userId ユーザーID
     * @return リダイレクト先
     */
    @RequestMapping("/admin/delete_user")
    public String deleteUserRegister(@RequestParam("user_id") Integer userId){

        // ユーザー削除
        boolean isDeleteUserResult = userService.deleteUserById(userId);

        if (isDeleteUserResult){
            // 成功
            return "redirect:/admin/user_list";
        } else {
            return "redirect:/error/not_delete_user_error";
        }
    }
    
    /**
     * ログイン画面表示
     * 
     * @return ログイン画面
     */
    @RequestMapping("/login")
    public String login(){

        // ログイン
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
        
        // ログアウト
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login?logout";
    }

    /**
     * 新規アカウント登録画面表示
     * 
     * @param signupValidation アカウント作成バリデーション
     * @return 新規アカウント登録画面
     */
    @RequestMapping("/sign_up")
    public String viewRegister(SignupValidation signupValidation){

        // アカウント作成
        return "auth/sign_up";
    }

    /**
     * パスワード変更画面表示
     * 
     * @param passwordChangeValidation パスワード変更バリデーション
     * @return パスワード変更画面
     */
    @RequestMapping("/change_password")
    public String viewChangePassword(PasswordChangeValidation passwordChangeValidation){

        // パスワード変更
        return "auth/change_password";
    }

    /**
     * 名前変更画面表示
     * 
     * @param nameChangeValidation 名前変更バリデーション
     * @return 名前変更画面
     */
    @RequestMapping("/change_name")
    public String viewChangeName(NameChangeValidation nameChangeValidation){

        // パスワード変更
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

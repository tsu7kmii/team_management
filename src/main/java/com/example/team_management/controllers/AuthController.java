package com.example.team_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team_management.models.user.User;
import com.example.team_management.services.UserService;

@Controller
public class AuthController {
    
    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String newUserRegister(@RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password){
        // 登録処理
        // 登録用インスタンス作成
        User user = new User();
    
        user.setUser_name(name);
        user.setAccess_token("sample");
        user.setRefresh_token("ssas");
        
        user.setAccess_expires_at(userService.localDateTimeFormatter("2024-09-24 23:51:08"));
        user.setRefresh_expires_at(userService.localDateTimeFormatter("2024-09-24 23:51:08"));
        user.setPassword(password);
        user.setPassword_salt("sese");
        
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
}

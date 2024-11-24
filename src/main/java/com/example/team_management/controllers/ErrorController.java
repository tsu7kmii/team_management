package com.example.team_management.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorController {
    
    @RequestMapping(value = "/error/{error}",method = RequestMethod.GET)
    public String error(@PathVariable String error, @RequestParam(required = false) String parms, Model model){

        model.addAttribute("error_title", error);

        String errorMessage = "this is error message";
        String returnLink = "/sample";

        switch (error) {
            case "create_account_error":
                errorMessage = "アカウント作成時にエラーが発生しました。 \n 再度試してください";
                returnLink = "/sign_up";
                break;

            case "used_email_error":
                errorMessage = "このメールアドレスは既に使用されています: " + parms + "。 \n 違うメールアドレスを使用してください";
                returnLink = "/sign_up";
                break;
        
            default:
                break;
        }

        model.addAttribute("error_message", errorMessage);
        model.addAttribute("return_link", returnLink);

        return "error";
    }
}

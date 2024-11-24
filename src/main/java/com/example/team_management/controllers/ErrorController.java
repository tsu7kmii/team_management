package com.example.team_management.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {
    
    @RequestMapping(value = "/error/{error}",method = RequestMethod.GET)
    public String error(@PathVariable String error, Model model){

        model.addAttribute("error_title", error);

        String errorMessage = "this is error message";
        String returnLink = "/sample";

        switch (error) {
            case "create_account":
                errorMessage = "アカウント作成時にエラーが発生しました。 \n 再度試してください";
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

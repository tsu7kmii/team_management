package com.example.team_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team_management.services.ErrorService;

@Controller
public class ErrorController {

    @Autowired
    ErrorService errorService;

    /**
     * エラーハンドリングを行うメソッド
     * @param error エラーコード
     * @param param 追加のパラメータ
     * @param model モデル
     * @return エラーページ
     */
    @RequestMapping(value = "/error/{error}", method = RequestMethod.GET)
    public String errorHangling(@PathVariable String error, @RequestParam(required = false) String param, Model model) {

        ErrorService.ErrorInfo errorInfo = errorService.getErrorInfo(error, param);

        model.addAttribute("error_title", error);
        model.addAttribute("error_message", errorInfo.getMessage());
        model.addAttribute("return_link", errorInfo.getLink());

        return "error";
    }

    /**
     * アクセス拒否時の処理を行うメソッド
     * @return アクセス拒否ページ
     */
    @RequestMapping("/access-denied")
    public String accessDenied() {

        // 権限エラー
		return "auth/access-denied";
	}
}

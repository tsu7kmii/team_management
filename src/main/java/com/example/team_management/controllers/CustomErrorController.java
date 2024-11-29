package com.example.team_management.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class CustomErrorController implements ErrorController{
    
    @RequestMapping("/error")
     public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404"; // 404.htmlテンプレートを返す
            }
        }
        return "error/error"; // デフォルトのエラーページ
    }

    /**
     * アクセス拒否時の処理を行うメソッド
     * @return アクセス拒否ページ
     */
    @RequestMapping("/access-denied")
    public String accessDenied() {

        // 権限エラー
		return "error/access-denied";
	}
    
}

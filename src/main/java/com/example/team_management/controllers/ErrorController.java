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
    public String errorHangling(@PathVariable String error, @RequestParam(required = false) String param, Model model){

        model.addAttribute("error_title", error);

        String errorMessage = "this is error message";
        String returnLink = "/sample";

        switch (error) {
            case "create_account_error":
                errorMessage = "アカウント作成時にエラーが発生しました。再度試してください";
                returnLink = "/sign_up";
                break;

            case "used_email_error":
                errorMessage = "このメールアドレスは既に使用されています: " + param + "。違うメールアドレスを使用してください";
                returnLink = "/sign_up";
                break;
        
            case "add_management_error":
                errorMessage = "マネジメントの登録時にエラーが発生しました。再度試してください。";
                returnLink = "/register_form";
                break;

            case "not_found_edit_item_error":
                errorMessage = "指定されたマネジメント項目は存在しないか、既に完了済の可能性があります。";
                returnLink = "/management";
                break;

            case "edit_management_error":
                errorMessage = "マネジメントの変更時にエラーが発生しました。再度試してください。";
                returnLink = "/management/edit/" + param;
                break;

            case "not_complate_management_error":
                errorMessage = "マネジメントの完了時にエラーが発生しました。再度試してください。";
                returnLink = "/management/edit/" + param;
                break;

            case "year_not_found_error":
                errorMessage = "指定された" + param + "年のデータは存在しません。";
                returnLink = "/management/history";
                break;

            case "no_history_data_error":
                errorMessage = "過去の完了済データは存在しません。";
                returnLink = "/management";
                break;

            case "not_equal_password_error":
                errorMessage = "入力されたパスワードが一致しません。";
                returnLink = "/change_password";
                break;

            case "not_change_password_error":
                errorMessage = "問題が発生しパスワードを変更できませんでした。再度お試しください。";
                returnLink = "/change_password";
                break;

            case "not_change_name_error":
                errorMessage = "問題が発生し名前を変更できませんでした。再度お試しください。";
                returnLink = "/change_naem";
                break;

            default:
                errorMessage = "エラーハンドリングが設定されてません";
                returnLink = "/";
                break;
        }

        model.addAttribute("error_message", errorMessage);
        model.addAttribute("return_link", returnLink);

        return "error";
    }
}

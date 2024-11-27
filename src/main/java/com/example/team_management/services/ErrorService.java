package com.example.team_management.services;

import org.springframework.stereotype.Service;

/**
 * エラーサービスクラス
 */
@Service
public class ErrorService {

    /**
     * エラー情報取得メソッド
     * 
     * @param error エラーコード
     * @param param 追加のパラメータ
     * @return エラー情報
     */
    public ErrorInfo getErrorInfo(String error, String param) {
        String errorMessage = "エラーハンドリングが設定されてません";
        String returnLink = "/";

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
                returnLink = "/view";
                break;

            case "edit_management_error":
                errorMessage = "マネジメントの変更時にエラーが発生しました。再度試してください。";
                returnLink = "/view/edit/" + param;
                break;

            case "not_complate_management_error":
                errorMessage = "マネジメントの完了時にエラーが発生しました。再度試してください。";
                returnLink = "/view/edit/" + param;
                break;

            case "year_not_found_error":
                errorMessage = "指定された" + param + "年のデータは存在しません。";
                returnLink = "/view/history";
                break;

            case "no_history_data_error":
                errorMessage = "過去の完了済データは存在しません。";
                returnLink = "/view";
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

            case "not_change_role_error":
                errorMessage = "問題が発生し権限レベルを変更できませんでした。再度お試しください。";
                returnLink = "/admin/user_list";
                break;

            case "not_delete_user_error":
                errorMessage = "問題が発生しユーザーを削除できませんでした。再度お試しください。";
                returnLink = "/admin/delete_user";
                break;

            default:
                errorMessage = "エラーハンドリングが設定されてません";
                returnLink = "/";
                break;
        }

        return new ErrorInfo(errorMessage, returnLink);
    }

    /**
     * エラー情報クラス
     */
    public static class ErrorInfo {
        private final String message;
        private final String link;

        /**
         * コンストラクタ
         * 
         * @param message エラーメッセージ
         * @param link リンク
         */
        public ErrorInfo(String message, String link) {
            this.message = message;
            this.link = link;
        }

        /**
         * メッセージ取得メソッド
         * 
         * @return メッセージ
         */
        public String getMessage() {
            return message;
        }

        /**
         * リンク取得メソッド
         * 
         * @return リンク
         */
        public String getLink() {
            return link;
        }
    }
}

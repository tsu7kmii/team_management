package com.example.team_management.exception;

/**
 * エラーメッセージを定義するクラス
 */
public final class ErrorMessages {
    private ErrorMessages() {}

    /**
     * ユーザー認証関係のエラーメッセージ
     */
    public static final class UserErros {
        public static final String ALREEADY_USED_EMAIL = "このメールアドレスは既に使用されています";
        
    }

    /**
     * 進捗管理関係のエラーメッセージ
     */
    public static final class ManagementError {

        public static final String NOT_FOUND_ITEM = "指定されたマネジメント項目は存在しないか、既に完了済の可能性があります。";
  
    }

    /**
     * 全体のエラーメッセージ
     */
    public static final class GlobalErrors {
    
        public static final String SQL_ERROR = "SQL実行時にエラーが発生しました。再度試してください";
    }
}

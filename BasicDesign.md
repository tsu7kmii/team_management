# 基本設計書
仮

## システム概要
チーム開発での進捗管理Webアプリケーションです。  
チームメンバーは各自アカウントを作成し、進捗を追加・管理します。

## 機能一覧
 - **認証機能**
    - ログイン
    - ログアウト
    - 新規アカウント作成
    - 名前変更
    - パスワード変更

 - **管理機能**
    - 進行中の進捗表示
    - 当年完了済み進捗表示
    - 年別完了済み進捗項目表示
    - 年別完了済み進捗表示
    - 進捗内容編集
    - 進捗完了
    - 新規進捗追加登録
    - ユーザー削除機能
    - ユーザー権限変更機能

## 画面一覧

 - **認証**
    - ログイン画面
    - 新規アカウント登録画面
    - 名前変更画面
    - パスワード変更画面

 - **管理**
    - トップ画面
    - 進行中・当年完了済み進捗表示画面
    - 年別完了済み進捗項目表示画面
    - 年別完了済み進捗表示画面
    - 進捗内容編集・完了画面
    - 新規進捗追加登録画面
    - ユーザー管理画面

 - **エラー**
    - エラー画面

## 画面仕様書

#### 認証
 - ログイン画面
    - `email`と`password`を使用
    - `DB:user_table`と照合し、入力されたメールのパスワードと一致するか確認
    - セッションに`username(DB:email)`,`password`,`role`を保存

 - 新規アカウント登録画面
    - 表示名、メールアドレス、パスワードを入力
    - バリデーションチェック
        - `name` : 3文字以上, 10文字以下
        - `email` : @が必要
        - `password` : 6文字以上, 20文字以下, アルファベット/数字を1文字以上必要、同じ文字の繰り返し使用不可
    - `DB:user_table`とメールアドレスの重複チェック
    - `DB:user_table`に追加

 - 名前変更画面
    - 名前を入力
    - バリデーションチェック : 3文字以上, 10文字以下
    - セッション : `username`より`DB:user_table`を更新

 - パスワード変更画面
    - 新しいパスワードを入力
    - 確認のため2回入力
    - バリデーションチェック : 6文字以上, 20文字以下, アルファベット/数字を1文字以上必要、同じ文字の繰り返し使用不可
    - セッション : `username`より`DB:user_table`を更新

#### 管理

 - 進行中・当年完了済み進捗表示画面
    - 進行中
        - 進行中の進捗を表示 (`DB:management`)
        - 見出し : 作成日/担当者/科目/課題リンク/ステータス/更新日/完了予定日/操作
            - 作成日 : `DB:create_at`
            - 担当者 : `DB:user_id` > `user_table`より名前を取得して表示
            - 科目 : `DB:subject`
            - 課題リンク : `DB:link`
            - ステータス : `DB:status`
                - `0` : 未着手
                - `1` : 取り組み中
                - `2` : 待機
                - `3` : レビュー待ち
                - `4` : 処理待ち
                - `5` : 完了
            - 更新日 : `DB:update_at`
            - 完了予定日 : `DB:completion_schedule`
            - 操作 : `Form button` : 編集完了ページに遷移
        - 作成日でソート

    - 完了済
        - 当年の完了済み進捗を表示 (`DB:management`)
        - 見出し : 作成日/担当者/科目/課題リンク/ステータス/更新日/完了日
        - 作成日 : `DB:create_at`
            - 担当者 : `DB:user_id` > `user_table`より名前を取得して表示
            - 科目 : `DB:subject`
            - 課題リンク : `DB:link`
            - ステータス : `DB:status`
                - `0` : 未着手
                - `1` : 取り組み中
                - `2` : 待機
                - `3` : レビュー待ち
                - `4` : 処理待ち
                - `5` : 完了
            - 更新日 : `DB:update_at`
            - 完了日 : `DB:delete_at`
        - 完了日でソート

 - 年別完了済み進捗項目表示画面
    - 年別で完了済み進捗を表示するための選択ページ
    - `DB:management` : `delete_at`より生成

 - 年別完了済み進捗表示画面
    - 年別完了済み進捗項目表示画面より遷移
    - 選択年ごとの完了済み進捗を表示
    
 - 進捗内容編集・完了画面
    - 進行中・当年完了済み進捗表示画面の進行中テーブル操作 : 編集ボタンより遷移
    - 編集ボタン
        - 担当者/科目/課題リンク/ステータス/完了予定日を変更可能
        - デフォルト入力済で選択の進捗内容を表示
        - 内容を変更し[Change]ボタンを押す
        - IDベースで内容を全て更新
        - リダイレクト : 進行中・当年完了済み進捗表示画面
        - ステータス : 完了済みがデフォルト入力で表示されることはあるが選択不可

    - 完了ボタン
        - ステータスを完了に変更
        - `delete_at`を追加
        - リダイレクト : 進行中・当年完了済み進捗表示画面

 - 新規進捗追加登録画面
    - 担当者/科目/課題リンク/ステータス/完了予定日を登録
        - 担当者 : デフォルトでログインアカウントの名前が選択済、変更可能
        - 科目 : 自由入力
        - 課題リンク : 自由入力
        - ステータス : デフォルトで取り組み中が選択済、変更可能
        - 完了予定日 : デフォルトで一週間後が選択済、変更可能

 - ユーザー管理画面
    - 管理者のみアクセス可能
    - 全有効ユーザーを表示
    - ユーザーの権限を変更可能
    - ユーザーを削除可能



## 画面遷移図

- アクセス
    - ログイン画面
        - トップ画面
            - 進行中・当年完了済み進捗表示画面
                - 進捗内容編集・完了画面 -> `redirect` : 進行中・当年完了済み進捗表示画面
                - 新規進捗追加登録画面 -> `redirect` : 進行中・当年完了済み進捗表示画面
                - 年別完了済み進捗項目表示画面 -> 略

            - 新規進捗追加登録画面 -> `redirect` : 進行中・当年完了済み進捗表示画面

            - 年別完了済み進捗項目表示画面
                - 年別完了済み進捗表示画面
                - 完了済み進捗が存在しない場合 -> `redirect` : エラーページ

            - 名前変更画面 -> `redirect` : トップ画面

            - パスワード変更画面 -> `redirect` : ログイン画面

            - ユーザー管理画面

            - ログアウトボタン -> `redirect` : ログイン画面

    - 新規アカウント登録画面 -> `redirect` : ログイン画面

    - エラーページ
        - getmappingや各種エラー発生時の吸収


# DB
## データベース設計書

### データベース: `team_management`

#### テーブル: `management`

- **management_id**: int, 主キー, オートインクリメント
- **user_id**: int, 外部キー, `user_table`の`user_id`を参照
- **subject**: text, 必須, 科目名
- **link**: text, 課題リンク
- **status**: int, 必須, 取り組み中などのステータス
- **create_at**: datetime, 必須, デフォルトで現在のタイムスタンプ
- **update_at**: datetime, 更新日時
- **delete_at**: datetime, 兼完了日
- **completion_schedule**: date, 必須, 完了予定日

#### テーブル: `user_table`

- **user_id**: int, 主キー, オートインクリメント
- **user_name**: varchar(20), 必須, ユーザー名
- **password**: varchar(100), 必須, パスワード
- **email**: varchar(50), 必須, ユニーク, メールアドレス
- **permission_level**: mediumint, 必須, 権限レベル (1: 管理者, 2: 一般ユーザー, 3: その他)
- **create_at**: datetime, 必須, デフォルトで現在のタイムスタンプ
- **update_at**: datetime, 更新日時
- **delete_at**: datetime, 削除日時

### インデックス

- `management`テーブル: `management_id`にプライマリキー
- `user_table`テーブル: `user_id`にプライマリキー, `email`にユニークキー

### AUTO_INCREMENT

- `management`テーブル: `management_id`の自動インクリメント
- `user_table`テーブル: `user_id`の自動インクリメント

### トランザクションと設定

- SQLモード: `NO_AUTO_VALUE_ON_ZERO`
- タイムゾーン: `+00:00`
- 文字セット: `utf8mb4`


# 開発環境と使用技術
MVC方式

## 開発環境

- **ビルドツール**: Apache Maven
- **Java Version**: 17

## フレームワーク

- **Spring Boot**: Version 3.4.0
  - **Spring Boot Starter Web**: Webアプリケーション開発用
  - **Spring Boot Starter Tomcat**: Tomcatサーバー
  - **Spring Boot Starter Thymeleaf**: テンプレートエンジン
  - **Spring Boot Starter JDBC**: JDBCサポート
  - **Spring Boot Starter Validation**: バリデーション機能
  - **Spring Boot Starter Test**: テスト用

## データベース

- **MySQL Connector/J**: MySQLデータベース接続用
- **ホスティング**: Dockerを使用してホスト
  - **MySQL**: `mysql:8.4`イメージを使用
  - **phpMyAdmin**: `phpmyadmin/phpmyadmin`イメージを使用

## セキュリティ

- **Spring Security Core**
- **Spring Security Web**
- **Spring Security Config**
- **Spring Security Test**: テスト用

## その他ライブラリ

- **Lombok**: コード生成
- **MyBatis Spring Boot Starter**: Version 3.0.3
- **WebJars**: フロントエンドライブラリ
  - **Bootstrap**: Version 5.3.1
  - **jQuery**: Version 3.7.0
  - **Flatpickr**: Version 4.6.13

# データベース設計
## ER図
![er](./img/image_er.png)

## テーブル定義
| システム名 | Team Management | 作成者 | あなたの名前 |
|------------|-----------------|--------|--------------|
| サブシステム名 | | 作成日 | 2024/11/23 |
| スキーマ名 | | RDBMS | MySQL |
| 論理テーブル名 | 管理テーブル | ENGINE | InnoDB |
| 物理テーブル名 | management | CHARSET | utf8mb4 |
| 備考 | | | |
| | 登録された管理情報を保存するテーブル。 | | |

| テーブル名 | カラム名              | データ型                | NULL許可 | デフォルト値          | コメント          |
|------------|-----------------------|-------------------------|----------|-----------------------|-------------------|
| management | management_id         | int AUTO_INCREMENT      | NO       |                       | PK                |
|            | user_id               | int                     | NO       |                       |                   |
|            | subject               | text                    | NO       |                       | 科目              |
|            | link                  | text                    | YES      | NULL                  |                   |
|            | status                | int                     | NO       |                       | 取り組み中など    |
|            | create_at             | datetime                | NO       | CURRENT_TIMESTAMP     |                   |
|            | update_at             | datetime                | YES      | NULL                  |                   |
|            | delete_at             | datetime                | YES      | NULL                  | 兼完了日          |
|            | completion_schedule   | date                    | NO       |                       | 完了予定日        |

| システム名 | Team Management | 作成者 | あなたの名前 |
|------------|-----------------|--------|--------------|
| サブシステム名 | | 作成日 | 2024/11/23 |
| スキーマ名 | | RDBMS | MySQL |
| 論理テーブル名 | ユーザーテーブル | ENGINE | InnoDB |
| 物理テーブル名 | user_table | CHARSET | utf8mb4 |
| 備考 | | | |
| | 登録されたユーザー情報を保存するテーブル。 | | |

| テーブル名 | カラム名              | データ型                | NULL許可 | デフォルト値          | コメント          |
|------------|-----------------------|-------------------------|----------|-----------------------|-------------------|
| user_table | user_id               | int AUTO_INCREMENT      | NO       |                       | PK                |
|            | user_name             | varchar(20)             | NO       |                       |                   |
|            | password              | varchar(100)            | NO       |                       |                   |
|            | email                 | varchar(50)             | NO       |                       | UK                |
|            | permission_level      | mediumint               | NO       |                       | 1:admin, 2:nomel user, 3 other |
|            | create_at             | datetime                | NO       | CURRENT_TIMESTAMP     |                   |
|            | update_at             | datetime                | YES      | NULL                  |                   |
|            | delete_at             | datetime                | YES      | NULL                  |                   |

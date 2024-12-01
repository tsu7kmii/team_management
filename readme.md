## イントロダクション
### `team_management`はチーム開発等における進捗管理システムです。
開発環境 : [pom.xml](./pom.xml)にはjava version17となっていますが、実際はjdk22で開発しています。  
データベースはmysqlを使用し、dockerでホストしています。  

フレームワークにspringを使用しています。

## 各種ドキュメント

### [ドキュメントレポジトリ ](https://github.com/tsu7kmii/team_management_doc)
上記レポジトリに、以下ドキュメント.xmlxのExcelファイルがあります。

> ### [基本設計書](https://github.com/tsu7kmii/team_management_doc/blob/main/docs/BasicDesign.xlsx)
> [基本設計書 - ブラウザで閲覧する](https://docs.google.com/spreadsheets/d/1r8WyOIZeNG6DBgNoc33U01sQ_q8_MHceTraFStcmHcA/edit?usp=drive_link)
> ### [画面仕様書](https://github.com/tsu7kmii/team_management_doc/blob/main/docs/ScreenDesign.xlsx)
> [画面仕様書 - ブラウザで閲覧する](https://docs.google.com/spreadsheets/d/1SAOyUtI7zFvJ6qnsg7l_n8SwbdyYAgAduBrj8LNxmYw/edit?usp=drive_link)
> ### [テーブル設計書](https://github.com/tsu7kmii/team_management_doc/blob/main/docs/TableDesign.xlsx)
> [テーブル設計書 - ブラウザで閲覧する](https://docs.google.com/spreadsheets/d/14bZGAYBpqBrf2632GxU1BIOzw0emZvEC_7QBHCDKOBo/edit?usp=drive_link)

## 使用方法

### サーバー起動
`mvn spring-boot:run`

### サーバーストップ

`Ctrl C` and y

### 接続
`localhost:8080`

### データベースの起動
root dir : `docker-compose up -d`

#### デフォルトのテーブルはデータが存在していません
`localhsot:4040`でphpmyadminに接続でき、`/SampleData` : [サンプルデータ入りのsql](./SampleData/team_management.sql)をインポートすることで、データ入りで確認することが出来ます。

id : `sample@a.a`  
ps : `sample1`  
サンプルデータを使用する場合、上記の情報で管理者アカウントでログインできます。

### メールの確認
`localhost:8025`

## ディレクトリ構成
/test_managemet  
│  
├── /.mvn /wrapper  
│   　　└── maven-wrapper.properties  
│  
├── /docker   　　- MySQLホスト用   
│   　　└── db  
│   　　   　　└── my.cnf  
│   　　   　　│   　　└── my.cnf  
│   　　   　　└── sql  
│   　　   　　   　　└── team_managemenet.sql  
│  
├── /SampleData   　　- デモ用サンプルデータ  
│   　　└── team_management.sql  
│  
├── /src  
│   　　├── /main  
│   　　│   　　├── /java /com /example /test_managemet  
│   　　│   　　│   　　├── /controllers   　　- コントローラーパッケージ  
│   　　│   　　│   　　│   　　├── AuthController.java   　　- 認証、新規登録、認証情報変更など  
│   　　│   　　│   　　│   　　├── CustomErrorController.java   
│   　　│   　　│   　　│   　　└── ManagementController.java   　　- 進捗管理の表示、登録、変更  
│   　　│   　　│   　　│  
│   　　│   　　│   　　├── /exception  
│   　　│   　　│   　　│   　　└── ErrorMessages.java   　　 - エラーメッセージ定数の定義  
│   　　│   　　│   　　│  
│   　　│   　　│   　　├── /models   　　- モデルパッケージ   
│   　　│   　　│   　　│   　　├── /dao   　　- DAOパッケージ  
│   　　│   　　│   　　│   　　│   　　├── impl   　　- implパッケージ  
│   　　│   　　│   　　│   　　│   　　│   　　├── ManagementDaoimpl.java    　　- デーベース接続  
│   　　│   　　│   　　│   　　│   　　│   　　└── UserDaoimpl.java   　　- デーベース接続  
│   　　│   　　│   　　│   　　│   　　├── ManagementDao.java    　　- DAO定義用インターフェース  
│   　　│   　　│   　　│   　　│   　　├── PasswordTokenRepository.java    　　- パスワードリセットトークンインターフェース  
│   　　│   　　│   　　│   　　│   　　└── UserDao.java   　　- DAO定義用インターフェース  
│   　　│   　　│   　　│   　　│  
│   　　│   　　│   　　│   　　└── /entity - エンティティパッケージ  
│   　　│   　　│   　　│   　　   　　├── Management.java   　　- DB:managementテーブル  
│   　　│   　　│   　　│   　　   　　├── PasswordResetToken.java   　　- DB:password_reset_token / password_reset_token_deqテーブル  
│   　　│   　　│   　　│   　　   　　└── User.java   　　- DB:user_tableテーブル  
│   　　│   　　│   　　│  
│   　　│   　　│   　　├── /request - リクエストパッケージ  
│   　　│   　　│   　　│   　　├── NamecheckRequest.java   　　- 名前変更時  
│   　　│   　　│   　　│   　　├── PasswordChangeRequest.java   　　- パスワード変更時  
│   　　│   　　│   　　│   　　└── SignupRequest.java   　　- 新規アカウント作成時  
│   　　│   　　│   　　│  
│   　　│   　　│   　　├── /services - サービスパッケージ  
│   　　│   　　│   　　│   　　├── MailSenderService.java   　　- メール送信  
│   　　│   　　│   　　│   　　├── ManagementService.java   　　- 進捗管理  
│   　　│   　　│   　　│   　　├── SecurityService.java   　　- パスワードリセットトークン認証  
│   　　│   　　│   　　│   　　├── UserDetailsServiceImpl.java   　　- ログイン認証  
│   　　│   　　│   　　│   　　└── UserService.java   　　- ユーザーアカウント管理  
│   　　│   　　│   　　│   
│   　　│   　　│   　　├── SecurityConfig.java   　　- 認証・権限関係の設定  
│   　　│   　　│   　　├── ServletInitializer.java  
│   　　│   　　│   　　└── TeamManagementApplication.java  
│   　　│   　　│  
│   　　│   　　└── /resources  
│   　　│   　　   　　├── /static /css    
│   　　│   　　   　　│   　　└── style.css   
│   　　│   　　   　　│  
│   　　│   　　   　　├── /templates   　　- thymeleafテンプレート  
│   　　│   　　   　　│   　　├── /auth   　　- 認証関係のパーツ  
│   　　│   　　   　　│   　　│   　　├── change_name.html  
│   　　│   　　   　　│   　　│   　　├── change_password.html  
│   　　│   　　   　　│   　　│   　　├── forget_password.html  
│   　　│   　　   　　│   　　│   　　├── login.html  
│   　　│   　　   　　│   　　│   　　├── sign_up.html  
│   　　│   　　   　　│   　　│   　　└── user_list.html  
│   　　│   　　   　　│   　　│  
│   　　│   　　   　　│   　　├── /common   　　- 共通パーツ  
│   　　│   　　   　　│   　　│   　　└── common.html  
│   　　│   　　   　　│   　　│  
│   　　│   　　   　　│   　　├── /error   　　- エラーパーツ  
│   　　│   　　   　　│   　　│   　　├── 404.html  
│   　　│   　　   　　│   　　│   　　├── access-denied.html  
│   　　│   　　   　　│   　　│   　　└── error.html  
│   　　│   　　   　　│   　　│  
│   　　│   　　   　　│   　　├── /management   　　- 進捗関係のパーツ  
│   　　│   　　   　　│   　　│   　　├── efit_item.html  
│   　　│   　　   　　│   　　│   　　├── history.html  
│   　　│   　　   　　│   　　│   　　├── management.html  
│   　　│   　　   　　│   　　│   　　├── register_item.html  
│   　　│   　　   　　│   　　│   　　└── year_history.html  
│   　　│   　　   　　│   　　│  
│   　　│   　　   　　│   　　└── index.html   　　- トップ画面  
│   　　│   　　   　　│  
│   　　│   　　   　　└── application.properties   　　- DB接続設定など   
│   　　└── /test /java /com /example /test_managemet  
│   　　   　　└── TeamManagementApplicationTests.java  
│   
├── .gitattributes  
├── .gitignore  
├── docker-compose.yml   　　- MySQLホスト用  
├── mvnw  
├── mvnw.cmd  
├── pom.xml - Project Object Model  
└── readme.md  

## イントロダクション
開発環境 : [pom.xml](./pom.xml)にはjava version17となっていますが、実際はjdk22で開発しています。  
データベースはmysqlを使用し、dockerでホストしています。  

フレームワークにspringを使用しています。

## 各種ドキュメント

### [ドキュメントレポジトリ ](https://github.com/tsu7kmii/team_management_doc)

> ### [基本設計書](https://github.com/tsu7kmii/team_management_doc/blob/main/docs/BasicDesign.xlsx)
> ### [画面仕様書](https://github.com/tsu7kmii/team_management_doc/blob/main/docs/ScreenDesign.xlsx)
> ### [テーブル設計書](https://github.com/tsu7kmii/team_management_doc/blob/main/docs/TableDesign.xlsx)


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
│   　　│   　　│   　　│   　　│   　　└── UserDao.java   　　- DAO定義用インターフェース  
│   　　│   　　│   　　│   　　│  
│   　　│   　　│   　　│   　　└── /entity - エンティティパッケージ  
│   　　│   　　│   　　│   　　   　　├── Management.java   　　- DB:managementテーブル  
│   　　│   　　│   　　│   　　   　　└── User.java   　　- DB:user_tableテーブル  
│   　　│   　　│   　　│  
│   　　│   　　│   　　├── /request - リクエストパッケージ  
│   　　│   　　│   　　│   　　├── NamecheckRequest.java   　　- 名前変更時  
│   　　│   　　│   　　│   　　├── PasswordChangeRequest.java   　　- パスワード変更時  
│   　　│   　　│   　　│   　　└── SignupRequest.java   　　- 新規アカウント作成時  
│   　　│   　　│   　　│  
│   　　│   　　│   　　├── /services - サービスパッケージ  
│   　　│   　　│   　　│   　　├── ManagementService.java   　　- 進捗管理  
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





<!-- 
## 参考元
[Spring Security バージョン6でのデータベース認証](https://qiita.com/L_A_P_119611/items/fc111bb23aca40b03cbb)  
[【Spring Security】SecurityFilterChainとカスタムUserDetailsService](https://zenn.dev/peishim/articles/c225ac5a5eedb0)  
[【初心者用】Spring Security でユーザー認証・登録を実装する手順のまとめ](https://qiita.com/t-yama-3/items/a538d47b8f0a27639d23) 
-->

<!-- 
## 今後の課題
 - メール機能の追加
 - パスワードリセット機能
 - 変更履歴機能 
 -->

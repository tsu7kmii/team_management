
開発環境 : [pom.xml](./pom.xml)にはjava version17となっていますが、実際はjdk22で開発しています。  
データベースはmysqlを使用し、dockerでホストしています。  
`localhsot:4040`でphpmyadminに接続でき、[サンプルデータ入りのsql](./SampleData/team_management.sql)をインポートすることで、データ入りで確認することが出来ます。

id : `sample@a.a`  
ps : `sample1`  
サンプルデータを使用する場合、上記の情報で管理者アカウントでログインできます。

フレームワークにspringを使用しています。

簡易ではありますが、基本設計書(?)を書きました。[こちら](./BasicDesign.md)より確認可能です。

## 

### server run
`mvn spring-boot:run`

### server stop

`Ctrl C` and y

### connect
`localhost:8080`

### db
#### no sample data
root dir : `docker-compose up -d`

##

#### sample sql
`/sampledbdata`   
id : `sample@a.a`  
ps : `sample1`  
other : メールのローカル部分がパスワード担っています

## 基本設計書
[こちら](./BasicDesign.md)

## 参考元
[Spring Security バージョン6でのデータベース認証](https://qiita.com/L_A_P_119611/items/fc111bb23aca40b03cbb)  
[【Spring Security】SecurityFilterChainとカスタムUserDetailsService](https://zenn.dev/peishim/articles/c225ac5a5eedb0)  
[【初心者用】Spring Security でユーザー認証・登録を実装する手順のまとめ](https://qiita.com/t-yama-3/items/a538d47b8f0a27639d23)


## 今後の課題
 - メール機能の追加
 - パスワードリセット機能
 - 変更履歴機能

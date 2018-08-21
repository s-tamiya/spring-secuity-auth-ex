Spring Boot + Spring SecurityでREST APIのサンプルを実装

参考にしたサイト：
Spring Security with Spring Boot 2.0で簡単なRest APIを実装する
https://qiita.com/rubytomato@github/items/6c6318c948398fa62275

動作確認用コマンド
■プレログイン
curl -i -c cookie.txt "http://localhost:8080/prelogin"

■ログイン
curl -i -b cookie.txt -c cookie.txt -X POST "http://localhost:8080/login" -d "email=testadmin1@example.com" -d "pass=test" -d "_csrf={CSRF_TOKEN}"

■ログアウト
curl -i -b cookie.txt -H "x-xsrf-token:{CSRF_TOKEN}" -X POST "http://localhost:8080/logout"

■認証後のAPIへのアクセス
curl -i -b cookie.txt "http://localhost:8080/hello/world"
curl -i -b cookie.txt "http://localhost:8080/user"
curl -i -b cookie.txt "http://localhost:8080/admin"

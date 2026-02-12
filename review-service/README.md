# 説明

このプロジェクトではアプリ開発用のSKILLsを作成し性能検証を行なっています。
サンプルとしてAPIをScalarDB/ Java21/ SpringBootで実装しました。
Zenn（https://zenn.dev/articles/2bade45ac9078a/） で公開したブログの詳細はこちらに記載されています。

## 事前準備

### 必要なコマンドとバージョン
- Java21
- docker-compose

### Schema Loaderのダウンロード
scalarDB Schema LoaderのJarファイルをローカルに落としてきてください。
```shell
curl -OL https://github.com/scalar-labs/scalardb/releases/download/v3.13.0/scalardb-schema-loader-3.13.0.jar
```

## アプリの起動方法

プロジェクトのルートで下記のコマンドを実行してください。

```shell
docker-compose up -d
docker exec -it mysql bash
mysql> mysql -u root -p # これでtest データベースと review テーブルが作成されているか確認
# mysqlが起動したら下記のコマンドを実行
java -jar scalardb-schema-loader-3.13.0.jar --config scalardb.properties --schema-file schema.json --coordinator
./gradlew bootRun
```

# 動作確認
```shell
# データの取得
curl -X GET http://localhost:8080/api/reviews | jq

# データの投入
curl -X POST http://localhost:8080/api/reviews \
    -H "Content-Type: application/json" \
    -d '{
      "productId": "PROD-12345",
      "userId": "USER-67890",
      "star": 5,
      "comment": "とても良い商品でした。配送も早く、梱包も丁寧でした。"
    }'
```
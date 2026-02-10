# 説明
このプロジェクトはReactとJavaで実装されているWebアプリです
裏側ではScalarDB, ScalarDB Clusterも利用しておりストレージはMySQLです
レビューを登録、参照できる機能を実装
Docker（MySQL）にデータをマウントしており、docker-compose donw -vコマンドでマウントを削除しない限りローカルにデータは残る

```shell
scalar.db.storage=jdbc
scalar.db.contact_points=jdbc:mysql://mysql:3306
scalar.db.username=root
scalar.db.password=root

# Standalone mode
scalar.db.cluster.node.standalone_mode.enabled=true

# SQL
scalar.db.sql.enabled=true

# License key configurations
scalar.db.cluster.node.licensing.license_key=<license_keyをもらう必要があり>
scalar.db.cluster.node.licensing.license_check_cert_pem=<pemを取得する必要あり>
```

```shell
curl -OL https://github.com/scalar-labs/scalardb/releases/download/v3.5.2/scalardb-schema-loader-3.5.2.jar

# schema loader 用の接続情報
touch scalardb.properties
# ローカルでScalarDB使うためのライセンスキーとかが書いてある
touch scalardb-cluster.properties
# ScalarDB Cluster と MySQLを立ち上げる用
touch docker-compose.yml
# schama loader経由でDBにテーブル作るためのJson
touch schema.json

java -jar scalardb-schema-loader.jar --config scalardb.properties --schema-file schema.json --coordinator
docker exec -it mysql bash
mysql> mysql -u root -p
docker-compose donw -v # volumeも削除してコンテナダウン

java -jar scalardb-schema-loader.jar --config scalardb.properties --schema-file schema.json --coordinator

```

ScalarDB Schema Loaderで型指定したいときに見た方がいいところ
https://scalardb.scalar-labs.com/ja-jp/docs/latest/schema-loader

## SKILLsの使い方
### 実装したい時
```shell
/coding
```

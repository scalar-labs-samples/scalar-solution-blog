# 守ってほしいコーディングルール

## 実装の際に守って欲しいコーディング規則

### 🚫 Record関連

* Recordにsetterを作らない - immutableの意味がなくなる
* Recordを継承しない - Recordは継承できない（finalクラス）
* Recordのフィールドをnullableにしない - できるだけ必須項目に
* RecordでBuilder使わない - コンストラクタ引数が多いならRecordじゃない方がいい
* Recordの更新時に元のインスタンスを捨てない - user.update(...)だけ書いて戻り値を無視

### 🚫 型推論（var）関連

## 型推論（var）

varは積極的に使用して構わないが、以下の点に注意すること：
- 型が不明瞭になる場合は明示的に書く
- チームメンバーから可読性の懸念が出たら柔軟に対応する

### 🚫 Null安全性

* nullチェックせずに使う - NullPointerException の温床
* Optional.get()を直接呼ぶ - ifPresent()、orElse()、orElseThrow()を使う
* Optionalをフィールドに持つ - Optionalは戻り値専用
* Optionalにnullをセットする - Optional.ofNullable(null) より Optional.empty()
* Optional<List>を返す - 空リストを返せばいい

### 🚫 Stream API

* forループで書けることをStreamで無理やり書く - 可読性重視
* Streamを再利用する - Stream は使い捨て、再利用するとエラー
* Stream内で副作用を起こす - forEach内で外部変数を変更など
* 並列Streamを安易に使う - parallelStream()は状況を選ぶ

### 🚫 文字列操作

* String連結で + を使う - StringBuilderかString.format()か Text Blocks
* Text Blocksを使わない - 複数行文字列は """..."""
* switch式を使わない - Java 14以降は switch式が簡潔

### 🚫 コレクション

* 生のコレクション型を使う - List list = new ArrayList(); → ジェネリクス使う
* Arrays.asListで変更しようとする - 固定長リストなので変更不可
* コレクションのnullチェック - 空コレクションを返す設計に
* List.of()の結果を変更 - immutableなので変更不可

### 🚫 古い書き方（Java 8以前）

* 匿名クラスをラムダで書ける場合 - ラムダ式を使う
* for文でインデックス回す - 拡張for文か Stream
* Date、Calendarクラス - LocalDateTime、ZonedDateTime使う
* new Integer(10) - Integer.valueOf(10) か自動ボクシング
* StringBuffer - StringBuilder使う（同期不要なら）

### 🚫 設計・アーキテクチャ

* DTOにビジネスロジック - DTOはデータ保持のみ
* Entityにsetterを全部作る - 必要最小限、不変性を意識
* staticメソッドだらけのUtilクラス - オブジェクト指向で設計
* God Object（何でも屋クラス） - 単一責任の原則
* public フィールド - private + getter/setterまたはRecord

### 🚫 Spring関連（Repositoryコードから推測）

* @Autowired をフィールドインジェクション - コンストラクタインジェクション推奨
* Repositoryに複雑なロジック - Serviceに書く
* Entity = DTO - 分離する

### 🚫 パフォーマンス

* ループ内でStringを + で連結 - StringBuilder使う
* ループ内でnew Object() - 可能なら外で生成
* リフレクションを多用 - 最終手段

### 🚫 その他

* マジックナンバー - 定数化する
* コメントで説明が必要なコード - コード自体を読みやすく
* 深いネスト（5段以上） - メソッド分割、早期return
* 長いメソッド（50行以上） - 分割検討
* var を無理に使う - 型を明示した方が読みやすい場合も多い

#### MUST DO

- `@EnableScalarDbRepositories` をメインクラスに付与
- `insert()` と `update()` を明示的に使い分け
- `@Transactional` をリポジトリに付与
- エンティティはイミュータブル（final フィールド）
- Service/Controllerには `@RequiredArgsConstructor` を付与（コンストラクタを手書きしない）
- Serviceクラスには `@Transactional` をクラスレベルで付与
- 読み取り専用メソッドには `@Transactional(readOnly = true)` を付与
- DTO は Java の `record` で定義する

#### MUST NOT

- `save()` メソッドを使用しない
- 複合主キーに `@Id` を使用しない
- `@Retryable` などのRetry処理を使用しない
- Serviceの各メソッドに `@Transactional` を付けない（クラスレベルのみ）
- `@GetMapping`/`@PostMapping`等に `value` 以外のパラメータを指定しない（produces等は不要）


### 重要な考え方：

* Immutability（不変性）を意識
* Null安全性を意識
* 関数型プログラミングの良いところを取り入れる
* Java 21の新機能を活かす（Record、Switch式、Text Blocks、Virtual Threads）
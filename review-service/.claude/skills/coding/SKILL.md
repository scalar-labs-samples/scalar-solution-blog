---
name: coding
description: |
  Spring Data JDBC for ScalarDB を使用したアプリケーション開発を支援します。
  エンティティ、リポジトリ、コントローラーの生成を行います。
allowed-tools:
  - Read
  - Write
  - Edit
  - Grep
  - Glob
  - Bash
---

# Spring Data JDBC for ScalarDB Development Skill

## Capabilities

1. **エンティティクラスの生成**
2. **リポジトリインターフェースの生成**
3. **サービスクラスの生成**
4. **コントローラーの生成**

## 実行フロー

```
/coding
```

このスキルは**対話型**で実行します。以下のステップを順番に進めてください。

---

### Step 1: 実装したい機能の概要を確認

ユーザーに以下を質問してください：

> **「どんな機能を実装しますか？」**
> 例: レビュー機能、ユーザー管理、商品検索 など

目的: 何を作るかの全体像を把握する。

---

### Step 2: インプットファイルの有無をまとめて確認

以下の2点を**まとめて**質問してください：

> **「以下のファイルがあれば教えてください（なくても大丈夫です）」**
>
> 1. **DDLファイル**（テーブル定義の `.sql` ファイル）
     >    - 例: `ddl.sql`, `schema.sql`
>    - エンティティやリポジトリの生成に使います
>
> 2. **API設計書**（Swagger/OpenAPI の `.yaml` または `.json` ファイル）
     >    - 例: `swagger.yaml`, `openapi.json`
>    - コントローラーやリクエスト/レスポンスDTOの生成に使います

**回答パターンに応じた分岐:**

| DDL | Swagger | 次のアクション |
|-----|---------|---------------|
| ✅ あり | ✅ あり | → Step 3 へ進む |
| ✅ あり | ❌ なし | → API仕様（エンドポイント、リクエスト/レスポンス形式）をヒアリングして Step 3 へ |
| ❌ なし | ✅ あり | → テーブル構造をヒアリング、または Swagger から推測して確認し Step 3 へ |
| ❌ なし | ❌ なし | → Step 1 の機能概要からテーブル構造・API仕様を一緒に設計して Step 3 へ |

---

### Step 3: 生成対象の確認

ユーザーに以下を質問してください：

> **「どこまで生成しますか？」**
>
> - [ ] エンティティ（Entity）
> - [ ] リポジトリ（Repository）
> - [ ] サービス（Service）
> - [ ] コントローラー（Controller）
> - [ ] 全部

---

### Step 4: コード生成

収集した情報をもとに、以下の順番でコードを生成してください。
この順番はレイヤードアーキテクチャの依存方向（内側 → 外側）に基づいています。

**生成順序:**

```
1. Entity        ← DDLをもとに生成
2. Repository    ← Entityをもとに生成
3. Service       ← Repository を利用。ビジネスロジックを実装。Entityを返す。
4. DTO           ← Swagger / API仕様をもとに Request DTO, Response DTO を生成
5. Controller    ← Service を利用。DTOとEntityの変換はここで行う。
```

※ コードの書き方は `architecture.md` と `coding-rule.md` に従うこと

---

### Step 5: ビルド確認

コード生成後、ビルドを実行してコンパイルエラーがないことを確認してください：

```bash
./gradlew build
```

- ビルドが失敗した場合は、エラーを修正して再度ビルドを実行
- ビルドが成功するまで繰り返す

---

### Step 6: 確認

生成したコードをユーザーに提示し、以下を確認してください：

> **「生成したコードを確認してください。修正点があれば教えてください。」**

## 参照ドキュメント

実装時は以下のパターンファイルを参照してください：

- `@.claude/standards/entity-patterns.md` - Entity の実装パターン
- `@.claude/standards/repository-patterns.md` - Repository の実装パターン
- `@.claude/standards/controller-patterns.md` - Controller の実装パターン
- `@.claude/standards/service-patterns.md` - Service の実装パターン

※ `architecture.md` と `coding-rule.md` は Rules として常に適用されます

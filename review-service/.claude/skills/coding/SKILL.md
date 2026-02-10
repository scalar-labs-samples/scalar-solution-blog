---
name: coding
description: |
  Spring Data JDBC for ScalarDB を使用したアプリケーション開発を支援します。
  エンティティ、リポジトリ、コントローラーの生成を行います。
argument-hint: "[swagger-path] [schema-path]"
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

## 実行モード

### 引数なし

```
/coding
```

**この場合はユーザーと対話して要件を確認:**
- 「何を実装しますか？」と質問
- エンティティ、リポジトリ、サービス、コントローラーのどれを作成するか確認
- 必要な情報をヒアリングしてから実装

## 参照ドキュメント

実装時は以下のパターンファイルを参照してください：

- `@.claude/standards/entity-patterns.md` - Entity の実装パターン
- `@.claude/standards/repository-patterns.md` - Repository の実装パターン
- `@.claude/standards/controller-patterns.md` - Controller の実装パターン
- `@.claude/standards/service-patterns.md` - Service の実装パターン

※ `architecture.md` と `coding-rule.md` は Rules として常に適用されます

## Key Constraints

### MUST DO

- `@EnableScalarDbRepositories` をメインクラスに付与
- `insert()` と `update()` を明示的に使い分け
- `@Transactional` をリポジトリに付与
- エンティティはイミュータブル（final フィールド）
- Service/Controllerには `@RequiredArgsConstructor` を付与（コンストラクタを手書きしない）
- Serviceクラスには `@Transactional` をクラスレベルで付与

### MUST NOT

- `save()` メソッドを使用しない
- 複合主キーに `@Id` を使用しない
- `@Retryable` などのRetry処理を使用しない
- Serviceの各メソッドに `@Transactional` を付けない（クラスレベルのみ）
- `@GetMapping`/`@PostMapping`等に `value` 以外のパラメータを指定しない（produces等は不要）

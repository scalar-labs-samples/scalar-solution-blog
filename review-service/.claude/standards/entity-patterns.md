# Entity 実装パターン

## 基本Template

```java
package <package>.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("<table_name>")
public record <EntityName>(
    @Id
    String id,
    <Type> <field1>,
    <Type> <field2>
) {
}
```

## ID 生成

ID は UUID v7 を使用して自分で生成する：

```java
import com.github.f4b6a3.uuid.UuidCreator;

// UUID v7 生成
String id = UuidCreator.getTimeOrderedEpoch().toString();
```

## 具体例

### 基本的な Entity

```java
@Table("items")
public record Item(
    @Id
    String id,
    String name,
    int price
) {
}
```

### 複合主キー（@Id なし）

```java
// ScalarDB SQL: PRIMARY KEY (order_id, item_id)
@Table("order_items")
public record OrderItem(
    String orderId,
    String itemId,
    int quantity
) {
}
```

### 複数フィールドの更新

```java
@Table("customers")
public record Customer(
    @Id
    String id,
    String name,
    int creditLimit,
    int creditTotal
) { }
```

## Record の自動生成メソッド

Record は以下を自動生成するため、明示的に書く必要なし：

- **コンストラクタ**: 全フィールドを引数に取る
- **getter**: `fieldName()` 形式（例: `item.price()`）
- **equals()**: フィールド値で比較
- **hashCode()**: フィールド値から生成
- **toString()**: `Item[id=..., name=Apple, price=100]` 形式

## 禁止パターン

```java
// Bad: class で定義
public class Item {
    private final String id;
    // ...
}

// Bad: Setter を定義（record ではコンパイルエラー）
public record Item(...) {
    public void setPrice(int price) { }
}

// Bad: ID を int や long で定義
public record Item(
    @Id
    int id  // String を使用すること
) {}

// Bad: リレーションを使用
public record Customer(
    @OneToMany
    List<Order> orders  // 禁止
) {}
```

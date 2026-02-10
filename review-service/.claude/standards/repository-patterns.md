# Repository 実装パターン

## 基本Template

```java
package <package>.domain.repository;

import com.scalar.db.sql.springdata.ScalarDbRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import <package>.domain.model.<Entity>;

@Transactional
@Repository
public interface <Entity>Repository extends ScalarDbRepository<<Entity>, String> {
    // insert(), findAll(), findById() などデフォルトで呼び出せるメソッドは書かない
    // カスタムメソッドはデフォルトで実装できない場合のみ追加
}
```

## デフォルトで使えるメソッド

以下は定義せずに使用可能：

| メソッド | 説明 |
|---------|------|
| `findById(String id)` | ID で取得（Optional を返す） |
| `findAll()` | 全件取得 |
| `insert(Entity entity)` | 新規挿入 |
| `update(Entity entity)` | 更新 |
| `delete(Entity entity)` | 削除 |
| `existsById(String id)` | 存在確認 |

## 具体例

### シンプルな Repository（推奨）

```java
@Transactional
@Repository
public interface ItemRepository extends ScalarDbRepository<Item, String> {
    // デフォルトメソッドで十分なので何も書かない
}
```

### 複合主キー用 Repository

複合主キーの場合のみ `@Query` でカスタムメソッドを追加：

```java
@Transactional
@Repository
public interface OrderItemRepository extends ScalarDbRepository<OrderItem, Void> {

    @Query("SELECT * FROM order_items WHERE order_id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") String orderId);

    @Query("SELECT * FROM order_items WHERE order_id = :orderId AND item_id = :itemId")
    Optional<OrderItem> findByOrderIdAndItemId(
        @Param("orderId") String orderId,
        @Param("itemId") String itemId
    );

    @Query("DELETE FROM order_items WHERE order_id = :orderId AND item_id = :itemId")
    void deleteByOrderIdAndItemId(
        @Param("orderId") String orderId,
        @Param("itemId") String itemId
    );
}
```

## カスタムメソッドが必要なケース

以下のような場合のみカスタムメソッドを追加：

1. **複合主キー** - デフォルトメソッドが使えない
2. **特定条件での検索** - `findByXxx()` が必要な場合
3. **集計クエリ** - COUNT, SUM などが必要な場合

```java
// 必要な場合のみ追加
@Query("SELECT * FROM items WHERE category = :category")
List<Item> findByCategory(@Param("category") String category);
```

## Service での使用例

```java
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    public void create(Item item) {
        itemRepository.insert(item);
    }

    public void update(Item item) {
        itemRepository.update(item);
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }
}
```

## 禁止パターン

```java
// Bad: save() を使用
itemRepository.save(item);  // 動作しない

// Bad: デフォルトメソッドを再定義
@Transactional
@Repository
public interface ItemRepository extends ScalarDbRepository<Item, String> {
    Optional<Item> findById(String id);  // 不要
    List<Item> findAll();                // 不要
}
```

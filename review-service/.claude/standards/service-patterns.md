# Service 実装パターン

## 基本Template

```java
package <package>.service;

import com.github.f4b6a3.uuid.UuidCreator;
import <package>.model.dto.request.<Entity>CreateRequest;
import <package>.model.entity.<Entity>;
import <package>.repository.<Entity>Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class <Entity>Service {

    private final <Entity>Repository repository;

    public List<<Entity>> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .toList();
    }

    public <Entity> findById(String id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("<Entity> not found: " + id));
    }

    public <Entity> create(<Entity>CreateRequest request) {
        var id = UuidCreator.getTimeOrderedEpoch().toString();
        var entity = new <Entity>(
            id,
            request.field1(),
            request.field2()
        );
        repository.insert(entity);
        return entity;
    }

    public <Entity> update(String id, <Entity>UpdateRequest request) {
        var entity = findById(id);
        var updated = entity.withField1(request.field1());
        repository.update(updated);
        return updated;
    }

    public void delete(String id) {
        var entity = findById(id);
        repository.delete(entity);
    }
}
```

## 必須ルール

| ルール | 説明 |
|--------|------|
| `@Transactional` | クラスレベルで付与（メソッド単位ではない） |
| `@RequiredArgsConstructor` | コンストラクタインジェクション（`@Autowired`フィールドインジェクション禁止） |
| `insert()` / `update()` | 明示的に使い分け（`save()` 禁止） |
| UUID v7 | `UuidCreator.getTimeOrderedEpoch().toString()` で自分で生成 |
| 戻り値 | Entity を返す（Response DTO は Controller で変換） |

## ID 生成

```java
import com.github.f4b6a3.uuid.UuidCreator;

// UUID v7 生成
String id = UuidCreator.getTimeOrderedEpoch().toString();
```


## 禁止パターン

```java
// Bad: save() を使用
repository.save(entity);  // 禁止

// Bad: @Autowired フィールドインジェクション
@Autowired
private ReviewRepository reviewRepository;  // 禁止

// Bad: メソッド単位で @Transactional
@Transactional  // クラスレベルのみ
public Review create(...) { }

// Bad: Response DTO を返す
public ReviewResponse create(...) {  // Entity を返すこと
    return new ReviewResponse(...);
}

// Bad: ID を外部から受け取る
public Review create(String id, ...) {  // UUID v7 を自分で生成すること
    ...
}
```

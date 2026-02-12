# Controller 実装パターン

## 基本Template

```java
package <package>.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import <package>.service.<Entity>Service;
import <package>.model.<Entity>;

@RestController
@RequestMapping(value = "/api/<entities>", produces = MediaType.APPLICATION_JSON_VALUE)
public class <Entity>Controller {

    @Autowired
    private final <Entity>Service service;

    @GetMapping
    public ResponseEntity<List<<Entity>>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<<Entity>> getById(@PathVariable <IdType> id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<<Entity>> create(@RequestBody <Entity>Request request) {
        <Entity> created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<<Entity>> update(
            @PathVariable <IdType> id,
            @RequestBody <Entity>Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable <IdType> id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

## 具体例

### ItemController

```java
@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {

    @Autowired
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable int id) {
        return ResponseEntity.ok(itemService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody ItemRequest request) {
        Item created = itemService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
```

## レスポンスパターン

### 成功レスポンス

```java
// 200 OK（取得・更新）
return ResponseEntity.ok(entity);

// 201 Created（新規作成）
return ResponseEntity.status(HttpStatus.CREATED).body(entity);

// 204 No Content（削除）
return ResponseEntity.noContent().build();
```

### リスト取得

```java
@GetMapping
public ResponseEntity<List<Item>> getAll() {
    List<Item> items = itemService.findAll();
    return ResponseEntity.ok(items);
}
```

### パスパラメータ

```java
@GetMapping("/{id}")
public ResponseEntity<Item> getById(@PathVariable int id) {
    return ResponseEntity.ok(itemService.getById(id));
}
```

### リクエストボディ

```java
@PostMapping
public ResponseEntity<Item> create(@RequestBody ItemRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(itemService.create(request));
}
```

## Request DTO 例

```java
public record ItemRequest(
    String name,
    int price
) {}
```

## Response DTO 例（必要な場合）

```java
public record ItemResponse(
    int itemId,
    String name,
    int price
) {
    public static ItemResponse from(Item item) {
        return new ItemResponse(item.itemId, item.name, item.price);
    }
}
```

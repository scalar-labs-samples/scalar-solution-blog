CREATE TABLE reviews (
    id TEXT PRIMARY KEY,
    product_id TEXT NOT NULL,
    user_id TEXT NOT NULL,
    star INT NOT NULL,
    comment TEXT
);
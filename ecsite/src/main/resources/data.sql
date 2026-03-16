-- カテゴリーデータ挿入
INSERT INTO categories (category_name) VALUES ('家電'), ('本'), ('ファッション');

-- ユーザーデータ挿入
INSERT INTO users (username, email, password,role) VALUES 
('admin', 'admin@example.com', '{noop}admin123','ROLE_ADMIN'),
('user1', 'user1@example.com', '{noop}pass1','ROLE_USER'),
('user2', 'user2@example.com', '{noop}password2','ROLE_USER');

-- 商品データ挿入
INSERT INTO products (product_name, description, price, stock_quantity, image_url, category_id) VALUES 
('ノートパソコン', '最新型のノートパソコン', 120000, 10, '/images/products/notebook.png', 1),
('電子書籍リーダー', '軽量な電子書籍リーダー', 15000, 25, '/images/products/ereader.png', 2),
('Tシャツ', '綿100%のシンプルなTシャツ', 20000, 50, '/images/products/tshirt.png', 3);

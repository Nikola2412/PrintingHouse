DROP DATABASE IF EXISTS printing_house;
CREATE DATABASE printing_house
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE printing_house;


CREATE TABLE users (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(255) NOT NULL UNIQUE,
    profile_image VARCHAR(255),
    role ENUM(
        'CLIENT_PHYSICAL',
        'CLIENT_LEGAL',
        'PRINTER',
        'ADMIN'
    ) NOT NULL,
    status ENUM(
        'PENDING',
        'ACTIVE',
        'REJECTED',
        'BLOCKED'
    ) NOT NULL DEFAULT 'PENDING',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE physical_clients (
    user_id BIGINT UNSIGNED PRIMARY KEY,
    CONSTRAINT fk_physical_client_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);


CREATE TABLE institutions (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    registration_number CHAR(8) NOT NULL UNIQUE,
    pib CHAR(9) NOT NULL UNIQUE,
    latitude DECIMAL(10,7),
    longitude DECIMAL(10,7),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE legal_clients (
    user_id BIGINT UNSIGNED PRIMARY KEY,
    institution_id BIGINT UNSIGNED NOT NULL UNIQUE,
    CONSTRAINT fk_legal_client_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_legal_client_institution
        FOREIGN KEY (institution_id) REFERENCES institutions(id)
        ON DELETE CASCADE
);

CREATE TABLE printers (
    user_id BIGINT UNSIGNED PRIMARY KEY,
    institution_id BIGINT UNSIGNED NOT NULL UNIQUE,
    CONSTRAINT fk_printer_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_printer_institution
        FOREIGN KEY (institution_id) REFERENCES institutions(id)
        ON DELETE CASCADE
);


CREATE TABLE password_reset_tokens (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    expires_at DATETIME NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reset_token_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);


CREATE TABLE categories (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE subcategories (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT UNSIGNED NOT NULL,
    name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    UNIQUE (category_id, name),
    CONSTRAINT fk_subcategory_category
        FOREIGN KEY (category_id) REFERENCES categories(id)
        ON DELETE CASCADE
);


CREATE TABLE products (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    printer_id BIGINT UNSIGNED NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    subcategory_id BIGINT UNSIGNED NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    stock_quantity INT UNSIGNED NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    like_count INT UNSIGNED NOT NULL DEFAULT 0,
    dislike_count INT UNSIGNED NOT NULL DEFAULT 0,
    image VARCHAR(50) NOT NULL DEFAULT '',

    UNIQUE (printer_id, code),

    CONSTRAINT fk_product_printer
        FOREIGN KEY (printer_id) REFERENCES printers(user_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_product_subcategory
        FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

CREATE TABLE product_colors (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT UNSIGNED NOT NULL,
    color_name VARCHAR(100) NOT NULL,

    UNIQUE (product_id, color_name),

    CONSTRAINT fk_product_color_product
        FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE CASCADE
);

CREATE TABLE product_images (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT UNSIGNED NOT NULL,
    file_name VARCHAR(255) NOT NULL,

    CONSTRAINT fk_product_image_product
        FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE CASCADE
);


CREATE TABLE printing_services (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    printer_id BIGINT UNSIGNED NOT NULL,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_service_printer
        FOREIGN KEY (printer_id) REFERENCES printers(user_id)
        ON DELETE CASCADE
);

CREATE TABLE product_services (
    product_id BIGINT UNSIGNED NOT NULL,
    service_id BIGINT UNSIGNED NOT NULL,
    additional_price DECIMAL(12,2) NOT NULL DEFAULT 0,
    max_width_mm INT UNSIGNED,
    max_height_mm INT UNSIGNED,

    PRIMARY KEY (product_id, service_id),

    CONSTRAINT fk_product_service_product
        FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_product_service_service
        FOREIGN KEY (service_id) REFERENCES printing_services(id)
        ON DELETE CASCADE
);


CREATE TABLE carts (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    client_user_id BIGINT UNSIGNED NOT NULL UNIQUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_cart_client
        FOREIGN KEY (client_user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE cart_items (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED NOT NULL,
    service_id BIGINT UNSIGNED,
    color_name VARCHAR(100),
    quantity INT UNSIGNED NOT NULL,
    custom_text TEXT,
    custom_image VARCHAR(255),
    unit_price DECIMAL(12,2) NOT NULL,
    service_price DECIMAL(12,2) NOT NULL DEFAULT 0,

    CONSTRAINT fk_cart_item_cart
        FOREIGN KEY (cart_id) REFERENCES carts(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_cart_item_product
        FOREIGN KEY (product_id) REFERENCES products(id),

    CONSTRAINT fk_cart_item_service
        FOREIGN KEY (service_id) REFERENCES printing_services(id)
);


CREATE TABLE orders (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    invoice_number VARCHAR(50) UNIQUE,
    client_user_id BIGINT UNSIGNED NOT NULL,
    printer_user_id BIGINT UNSIGNED NOT NULL,

    status ENUM(
        'ORDERED',
        'PAID',
        'IN_PRINT',
        'DELIVERED',
        'RECEIVED',
        'CANCELLED'
    ) NOT NULL DEFAULT 'ORDERED',

    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
    ordered_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    paid_at DATETIME,
    delivered_at DATETIME,
    received_at DATETIME,

    CONSTRAINT fk_order_client
        FOREIGN KEY (client_user_id) REFERENCES users(id),

    CONSTRAINT fk_order_printer
        FOREIGN KEY (printer_user_id) REFERENCES printers(user_id)
);

CREATE TABLE order_items (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED NOT NULL,
    service_id BIGINT UNSIGNED,
    product_name VARCHAR(200) NOT NULL,
    quantity INT UNSIGNED NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    service_price DECIMAL(12,2) NOT NULL DEFAULT 0,
    color_name VARCHAR(100),
    custom_text TEXT,
    custom_image VARCHAR(255),
    subtotal DECIMAL(12,2) NOT NULL,

    CONSTRAINT fk_order_item_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_order_item_product
        FOREIGN KEY (product_id) REFERENCES products(id),

    CONSTRAINT fk_order_item_service
        FOREIGN KEY (service_id) REFERENCES printing_services(id)
);

CREATE TABLE payments (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT UNSIGNED NOT NULL,
    amount DECIMAL(12,2) NOT NULL,

    payment_status ENUM(
        'PENDING',
        'SUCCESS',
        'FAILED'
    ) NOT NULL DEFAULT 'PENDING',

    provider VARCHAR(50),
    transaction_id VARCHAR(255),
    paid_at DATETIME,

    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
        ON DELETE CASCADE
);

CREATE TABLE product_reviews (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT UNSIGNED NOT NULL,
    client_user_id BIGINT UNSIGNED NOT NULL,

    reaction ENUM('LIKE', 'DISLIKE') NOT NULL,
    comment TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (product_id, client_user_id),

    CONSTRAINT fk_review_product
        FOREIGN KEY (product_id) REFERENCES products(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_review_client
        FOREIGN KEY (client_user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE procurements (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    client_user_id BIGINT UNSIGNED NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at DATETIME NOT NULL,

    status ENUM(
        'OPEN',
        'CLOSED',
        'AWARDED',
        'CANCELLED'
    ) NOT NULL DEFAULT 'OPEN',

    winning_offer_id BIGINT UNSIGNED NULL,

    CONSTRAINT fk_procurement_client
        FOREIGN KEY (client_user_id) REFERENCES legal_clients(user_id)
);

CREATE TABLE procurement_items (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    procurement_id BIGINT UNSIGNED NOT NULL,
    product_id BIGINT UNSIGNED NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    quantity INT UNSIGNED NOT NULL,

    CONSTRAINT fk_procurement_item_procurement
        FOREIGN KEY (procurement_id) REFERENCES procurements(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_procurement_item_product
        FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE procurement_offers (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    procurement_id BIGINT UNSIGNED NOT NULL,
    printer_user_id BIGINT UNSIGNED NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    submitted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    status ENUM(
        'SUBMITTED',
        'WINNING',
        'REJECTED'
    ) NOT NULL DEFAULT 'SUBMITTED',

    UNIQUE (procurement_id, printer_user_id),

    CONSTRAINT fk_offer_procurement
        FOREIGN KEY (procurement_id) REFERENCES procurements(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_offer_printer
        FOREIGN KEY (printer_user_id) REFERENCES printers(user_id)
);

CREATE TABLE procurement_offer_items (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    offer_id BIGINT UNSIGNED NOT NULL,
    procurement_item_id BIGINT UNSIGNED NOT NULL,
    quantity INT UNSIGNED NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,

    CONSTRAINT fk_offer_item_offer
        FOREIGN KEY (offer_id) REFERENCES procurement_offers(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_offer_item_procurement_item
        FOREIGN KEY (procurement_item_id) REFERENCES procurement_items(id)
        ON DELETE CASCADE
);

ALTER TABLE procurements
    ADD CONSTRAINT fk_procurement_winning_offer
    FOREIGN KEY (winning_offer_id)
    REFERENCES procurement_offers(id)
    ON DELETE SET NULL;


CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_status ON users(status);

CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_printer ON products(printer_id);
CREATE INDEX idx_products_subcategory ON products(subcategory_id);
CREATE INDEX idx_products_active_stock ON products(active, stock_quantity);

CREATE INDEX idx_orders_client ON orders(client_user_id);
CREATE INDEX idx_orders_printer ON orders(printer_user_id);
CREATE INDEX idx_orders_status ON orders(status);

CREATE INDEX idx_reviews_product ON product_reviews(product_id);

CREATE INDEX idx_procurement_status_expiry
    ON procurements(status, expires_at);


INSERT INTO categories (name) VALUES
('Štampa malih formata'),
('Štampa velikih formata'),
('Kreativne štampe');

INSERT INTO subcategories (category_id, name)
SELECT id, 'Olovke'
FROM categories
WHERE name = 'Štampa malih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Vizit karte'
FROM categories
WHERE name = 'Štampa malih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Flajeri'
FROM categories
WHERE name = 'Štampa malih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Zahvalnice'
FROM categories
WHERE name = 'Štampa malih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Pozivnice'
FROM categories
WHERE name = 'Štampa malih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Fascikle'
FROM categories
WHERE name = 'Štampa malih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Posteri'
FROM categories
WHERE name = 'Štampa velikih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Rollups'
FROM categories
WHERE name = 'Štampa velikih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Fototapete'
FROM categories
WHERE name = 'Štampa velikih formata';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Šolje'
FROM categories
WHERE name = 'Kreativne štampe';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Štampa na majicama'
FROM categories
WHERE name = 'Kreativne štampe';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Štampa na duksevima'
FROM categories
WHERE name = 'Kreativne štampe';

INSERT INTO subcategories (category_id, name)
SELECT id, 'Štampa na cegerima'
FROM categories
WHERE name = 'Kreativne štampe';

INSERT INTO users (
    username,
    password_hash,
    first_name,
    last_name,
    email,
    role,
    status
)
VALUES (
    'admin',
    'REPLACE_WITH_BCRYPT_HASH',
    'System',
    'Administrator',
    'admin@printinghouse.local',
    'ADMIN',
    'ACTIVE'
);

INSERT INTO users (
    username,
    password_hash,
    first_name,
    last_name,
    phone,
    email,
    profile_image,
    role,
    status
)
VALUES (
    'copystudio',
    '$2a$10$OVDE_IDE_BCRYPT_HASH',
    'Marko',
    'Marković',
    '0611234567',
    'info@copystudio.rs',
    'default_profile_image.jpg',
    'PRINTER',
    'ACTIVE'
);

INSERT INTO institutions (
    name,
    address,
    city,
    registration_number,
    pib,
    latitude,
    longitude
)
VALUES (
    'Copy Studio',
    'Kumanovska 15',
    'Beograd',
    '12345678',
    '123456789',
    44.8040,
    20.4651
);

INSERT INTO printers (
    user_id,
    institution_id
)
VALUES (
    1,
    1
);

INSERT INTO products (
    printer_id,
    code,
    name,
    description,
    subcategory_id,
    unit_price,
    stock_quantity,
    like_count,
    dislike_count,
    image
)
VALUES (
    1,
    'PR-001',
    'Pamučna polo majica',
    'Pamučna majica 180g pogodna za DTG štampu.',
    11,
    1200.00,
    150,
    10,
    0,
    "polo_majica.jpg"
),(
    1,
    'PR-002',
    'Pamučna majica sa printom',
    'Pamučna majica 180g sa printom.',
    11,
    1500.00,
    100,
    20,
    0,
    "pamučna_majica_sa_printom.jpg"
),(
    1,
    'PR-003',
    'Hemijska olovka',
    'Olovka sa plastičnim kućištem i logotipom.',
    1,
    150.00,
    100,
    2,
    0,
    "hemijska_olovka.jpg"
),(
    1,
    'PR-004',
    'Fascikle sa printom',
    'Fascikle sa printom po želji klijenta.',
    6,
    75.00,
    100,
    5,
    0,
    "fascikle_sa_printom.jpg"
),(
    1,
    'PR-005',
    'Pozivnice',
    'Pozivnice sa printom po želji klijenta.',
    5,
    75.00,
    100,
    30,
    0,
    "pozivnice.jpg"
),(
    1,
    'PR-006',
    'Rollup 85x200cm',
    'Rollup sa printom po želji klijenta.',
    8,
    3500.00,
    50,
    50,
    0,
    "rollup.jpg"
),(
    1,
    'PR-007',
    'Fototapeta 200x300cm',
    'Fototapeta sa printom po želji klijenta.',
    9,
    12000.00,
    20,
    75,
    0,
    "fototapeta.jpg"
);


INSERT INTO product_colors(product_id,color_name)
VALUES
(1,'Bela'),
(1,'Crna'),
(1,'Siva'),
(1,'Tamno plava');


INSERT INTO printing_services(
    printer_id,
    name,
    description
)
VALUES(
    1,
    'DTG štampa',
    'Direktna štampa na tekstil'
);


INSERT INTO product_services(
    product_id,
    service_id,
    additional_price,
    max_width_mm,
    max_height_mm
)
VALUES(
    1,
    1,
    350,
    300,
    400
);


INSERT INTO product_images(
    product_id,
    file_name,
)
VALUES(
    1,
    'majica.jpg',
);
CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DOUBLE NOT NULL,
                          image_url VARCHAR(512) NOT NULL
);

CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         role VARCHAR(50) DEFAULT 'USER'
);

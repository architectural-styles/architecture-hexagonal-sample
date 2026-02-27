DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id         VARCHAR(36) PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL
);
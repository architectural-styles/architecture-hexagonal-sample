CREATE TABLE IF NOT EXISTS users (
    id         VARCHAR(36) PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL
);
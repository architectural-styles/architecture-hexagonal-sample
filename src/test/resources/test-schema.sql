DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id         VARCHAR(36) PRIMARY KEY,  -- уточнить до 36 (длина UUID)
    name       VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL
);
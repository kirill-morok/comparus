CREATE TABLE users (
    user_id    VARCHAR(64) PRIMARY KEY,
    login      VARCHAR(64) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
);

INSERT INTO users (user_id, login, first_name, last_name) VALUES
    ('example-user-id-1', 'user-1', 'User', 'Userenko'),
    ('example-user-id-3', 'user-3', 'Maria', 'Ivanova');
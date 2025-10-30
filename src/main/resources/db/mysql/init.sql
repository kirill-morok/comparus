CREATE TABLE user_table (
    ldap_login VARCHAR(64) PRIMARY KEY,
    name       VARCHAR(100),
    surname    VARCHAR(100)
);
INSERT INTO user_table (ldap_login, name, surname) VALUES
    ('example-user-id-2', 'Testuser', 'Testov'),
    ('example-user-id-1', 'User', 'Userenko');
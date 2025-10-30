package com.comparus.basic;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.TimeZone;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestContainersConfig {

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));
    }

    @Container
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("db1")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("db/postgres/init.sql")
            .withReuse(true);

    @Container
    protected static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("db2")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("db/mysql/init.sql")
            .withReuse(true);


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("data-sources.sources[0].name", () -> "pg");
        registry.add("data-sources.sources[0].strategy", () -> "postgres");
        registry.add("data-sources.sources[0].url", postgres::getJdbcUrl);
        registry.add("data-sources.sources[0].user", postgres::getUsername);
        registry.add("data-sources.sources[0].password", postgres::getPassword);
        registry.add("data-sources.sources[0].table", () -> "users");
        registry.add("data-sources.sources[0].mapping.id", () -> "user_id");
        registry.add("data-sources.sources[0].mapping.username", () -> "login");
        registry.add("data-sources.sources[0].mapping.name", () -> "first_name");
        registry.add("data-sources.sources[0].mapping.surname", () -> "last_name");

        registry.add("data-sources.sources[1].name", () -> "my");
        registry.add("data-sources.sources[1].strategy", () -> "mysql");
        registry.add("data-sources.sources[1].url", mysql::getJdbcUrl);
        registry.add("data-sources.sources[1].user", mysql::getUsername);
        registry.add("data-sources.sources[1].password", mysql::getPassword);
        registry.add("data-sources.sources[1].table", () -> "user_table");
        registry.add("data-sources.sources[1].mapping.id", () -> "ldap_login");
        registry.add("data-sources.sources[1].mapping.username", () -> "ldap_login");
        registry.add("data-sources.sources[1].mapping.name", () -> "name");
        registry.add("data-sources.sources[1].mapping.surname", () -> "surname");
    }
}

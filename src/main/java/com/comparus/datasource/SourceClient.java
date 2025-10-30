package com.comparus.datasource;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SourceClient {

    private final JdbcTemplate jdbc;

    public SourceClient(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate jdbc() {
        return jdbc;
    }
}

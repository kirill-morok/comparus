package com.comparus.datasource;

import com.comparus.configuration.properties.DataSourcesProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SqlBuilder {

    public String build(DataSourcesProperties.DataSourceConfig cfg,
                        String username, String name, String surname) {
        var sql = new StringBuilder();
        sql.append(String.format("""
                SELECT
                    %s AS id,
                    %s AS username,
                    %s AS name,
                    %s AS surname
                FROM %s
                """,
                cfg.mapping().get("id"),
                cfg.mapping().get("username"),
                cfg.mapping().get("name"),
                cfg.mapping().get("surname"),
                cfg.table()
        ));

        var conditions = new ArrayList<String>();

        if (username != null && !username.isEmpty()) {
            conditions.add(String.format("%s = '%s'",
                    cfg.mapping().get("username"), escapeSql(username)));
        }

        if (name != null && !name.isEmpty()) {
            conditions.add(String.format("%s = '%s'",
                    cfg.mapping().get("name"), escapeSql(name)));
        }

        if (surname != null && !surname.isEmpty()) {
            conditions.add(String.format("%s = '%s'",
                    cfg.mapping().get("surname"), escapeSql(surname)));
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions));
        }

        return sql.toString();
    }

    private String escapeSql(String value) {
        if (value == null) {
            return null;
        }
        return value.replace("'", "''");
    }
}

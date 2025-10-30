package com.comparus.datasource;

import com.comparus.configuration.properties.DataSourcesProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Slf4j
public class SourceRegistry {

    private final Map<String, SourceClient> clients = new HashMap<>();

    public SourceRegistry(DataSourceFactory factory) {
        factory.getDataSources().forEach((name, ds) -> {
            clients.put(name, new SourceClient(ds));
        });
    }

    public JdbcTemplate getJdbcTemplateBySourceName(String sourceName) {
        var client = clients.get(sourceName);
        if (client == null) {
            log.error("DataSource is missing by source name: {}", sourceName);
            throw new IllegalStateException(String.format("DataSource is missing by source name: %s", sourceName));
        }
        return client.jdbc();
    }
}
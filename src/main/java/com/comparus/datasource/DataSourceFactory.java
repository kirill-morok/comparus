package com.comparus.datasource;

import com.comparus.configuration.properties.DataSourcesProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Slf4j
public class DataSourceFactory {

    private final static int POOL_SIZE = 5;
    private final static String POOL_NAME_STR_BUILDER = "%s-pool";

    private final Map<String, DataSource> dataSources = new HashMap<>();

    public DataSourceFactory(DataSourcesProperties props) {
        try {
            props.sources().forEach(src -> {
                log.info("Creating DataSource: {}", src.name());
                var config = new HikariConfig();
                config.setJdbcUrl(src.url());
                config.setUsername(src.user());
                config.setPassword(src.password());
                config.setMaximumPoolSize(POOL_SIZE);
                config.setPoolName(String.format(POOL_NAME_STR_BUILDER, src.name()));
                var ds = new HikariDataSource(config);
                dataSources.put(src.name(), ds);
                log.info("Created DataSource: {}", src.name());
            });
        } catch (Exception e) {
            log.error("Failed to create DataSource. reason: {}", e.getMessage());
            throw new IllegalStateException("Failed to initialize data source: " + e.getMessage(), e);
        }
    }

}

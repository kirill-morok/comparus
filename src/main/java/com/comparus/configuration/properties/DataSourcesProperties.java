package com.comparus.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "data-sources")
public record DataSourcesProperties(
        List<DataSourceConfig> sources) {

    public record DataSourceConfig(
            String name,
            String strategy,
            String table,
            String url,
            String user,
            String password,
            Map<String, String> mapping
    ) {
    }

}

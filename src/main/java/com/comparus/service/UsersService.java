package com.comparus.service;

import com.comparus.api.dto.UserDto;
import com.comparus.datasource.SourceRegistry;
import com.comparus.datasource.SqlBuilder;
import com.comparus.configuration.properties.DataSourcesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final DataSourcesProperties dataSourcesProperties;
    private final SourceRegistry registry;
    private final SqlBuilder sqlBuilder;

    public List<UserDto> getUsers(String username, String name, String surname) {
        return dataSourcesProperties.sources().stream()
                .map(conf -> prepareUsers(conf, username, name, surname))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        UserDto::id,
                        identity(),
                        (existing, replacement) -> existing))
                .values()
                .stream()
                .toList();
    }

    private List<UserDto> prepareUsers(DataSourcesProperties.DataSourceConfig conf,
                                       String username, String name, String surname) {
        var jdbc = registry.getJdbcTemplateBySourceName(conf.name());
        var sql = sqlBuilder.build(conf, username, name, surname);
        return jdbc.query(sql, (rs, i) -> new UserDto(
                rs.getString("id"),
                rs.getString("username"),
                rs.getString("name"),
                rs.getString("surname")
        ));
    }

}

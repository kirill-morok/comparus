package com.comparus;

import com.comparus.configuration.properties.DataSourcesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
})
@EnableConfigurationProperties({DataSourcesProperties.class})
public class ComparusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComparusApplication.class, args);
    }

}

package com.company.sample;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MainStoreConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}

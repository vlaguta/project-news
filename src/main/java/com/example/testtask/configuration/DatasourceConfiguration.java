package com.example.testtask.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @Profile("test")
    public DataSource testDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:mem:test");
        dataSourceBuilder.username("user");
        dataSourceBuilder.password("pass");
        return dataSourceBuilder.build();
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:54320/dbTest");
        dataSourceBuilder.username("user");
        dataSourceBuilder.password("pass");
        return dataSourceBuilder.build();
    }
}

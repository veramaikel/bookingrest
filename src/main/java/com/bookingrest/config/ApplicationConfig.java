package com.bookingrest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Value("classpath:sql.properties")
    private Resource sqlResource;

    @Bean
    public DataSource getDataSource() throws IOException {

        Properties props = new Properties();
        props.load(sqlResource.getInputStream());

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url((String)props.get("url"));
        dataSourceBuilder.username((String)props.get("username"));
        dataSourceBuilder.password((String)props.get("password"));
        return dataSourceBuilder.build();
    }
}

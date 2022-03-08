package com.bookingrest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() throws IOException {
        FileInputStream propertiesInput = new FileInputStream("../sql.properties");

        Properties props = new Properties();
        props.load(propertiesInput);

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url((String)props.get("url"));
        dataSourceBuilder.username((String)props.get("username"));
        dataSourceBuilder.password((String)props.get("password"));
        return dataSourceBuilder.build();
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer pageableResolverCustomizer() {

        return resolver -> resolver.setFallbackPageable(PageRequest.of(0,
                Integer.parseInt(env.getProperty("spring.data.pageable.default-page-size"))));
    }
}

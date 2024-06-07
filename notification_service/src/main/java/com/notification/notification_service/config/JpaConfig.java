package com.notification.notification_service.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JpaConfig {
    
    @Value("${spring.datasource.url}")
    private String host;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String passworrd;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    public DataSource driverManager(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(host);
        dataSource.setUsername(user);
        dataSource.setPassword(passworrd);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

}

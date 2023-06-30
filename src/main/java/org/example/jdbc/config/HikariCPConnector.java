package org.example.jdbc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.utils.ConfPropertiesUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPConnector {

    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private static DataSource getDataSource() {
        if (dataSource == null) {
//            System.out.println("Creating new datasource");
            createDataSource();
        }
        return dataSource;
    }

    private static void createDataSource() {
//        System.out.println("HikariConfig is ready");
//        System.out.println("Getting DataSource");
        dataSource = new HikariDataSource(getHikariConfig());
    }

    private static HikariConfig getHikariConfig() {
//        System.out.println("Creating the config with HikariConfig with pool size 5");

        HikariConfig hikariConfig = new HikariConfig();

        // register driver
        hikariConfig.setDriverClassName(ConfPropertiesUtil.getValue("driver"));
        // set url
        hikariConfig.setJdbcUrl(ConfPropertiesUtil.getValue("url"));
        // set user and password
        hikariConfig.setUsername(ConfPropertiesUtil.getValue("user"));
        hikariConfig.setPassword(ConfPropertiesUtil.getValue("password"));
        // set pool name
        hikariConfig.setPoolName("`banking_system` db pool");
        // set pool size
        int poolSize = Integer.parseInt(ConfPropertiesUtil.getValue("poolSize"));
        hikariConfig.setMaximumPoolSize(poolSize);
        // set connection timeout
//        hikariConfig.setConnectionTimeout(Duration.ofSeconds(20).toMillis());
        // set max time connection sit in pool
//        hikariConfig.setIdleTimeout(Duration.ofMinutes(1).toMillis());

        return hikariConfig;
    }

}

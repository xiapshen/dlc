/**
 * Copyright  2017
 *
 * All  right  reserved.
 *
 * Created  on  2017年7月30日 下午7:40:00
 *
 * @Package com.happygo.dlc.dal.mybatis
 * @Title: DruidDataSourceConf.java
 * @Description: DruidDataSourceConf.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.dal.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * ClassName:DruidDataSourceConf
 * @Description: DruidDataSourceConf.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月30日 下午7:40:00
 */
@Configuration
@PropertySource("datasource.properties")
public class DruidDataSourceConf {

    /**
     * Logger the LOGGER
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * The Db url.
     */
    @Value("${dlc.web.druid.datasource.url}")
    private String dbUrl;

    /**
     * The Username.
     */
    @Value("${dlc.web.druid.datasource.username}")
    private String username;

    /**
     * The Password.
     */
    @Value("${dlc.web.druid.datasource.password}")
    private String password;

    /**
     * The Driver class name.
     */
    @Value("${dlc.web.druid.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * The Initial size.
     */
    @Value("${dlc.web.druid.datasource.initialSize}")
    private int initialSize;

    /**
     * The Min idle.
     */
    @Value("${dlc.web.druid.datasource.minIdle}")
    private int minIdle;

    /**
     * The Max active.
     */
    @Value("${dlc.web.druid.datasource.maxActive}")
    private int maxActive;

    /**
     * The Max wait.
     */
    @Value("${dlc.web.druid.datasource.maxWait}")
    private int maxWait;

    /**
     * The Time between eviction runs millis.
     */
    @Value("${dlc.web.druid.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    /**
     * The Min evictable idle time millis.
     */
    @Value("${dlc.web.druid.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    /**
     * The Validation query.
     */
    @Value("${dlc.web.druid.datasource.validationQuery}")
    private String validationQuery;

    /**
     * The Test while idle.
     */
    @Value("${dlc.web.druid.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    /**
     * The Test on borrow.
     */
    @Value("${dlc.web.druid.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    /**
     * The Test on return.
     */
    @Value("${dlc.web.druid.datasource.testOnReturn}")
    private boolean testOnReturn;

    /**
     * The Pool prepared statements.
     */
    @Value("${dlc.web.druid.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    /**
     * The Max open prepared statements.
     */
    @Value("${dlc.web.druid.datasource.maxOpenPreparedStatements}")
    private int maxOpenPreparedStatements;

    /**
     * The Filters.
     */
    @Value("${dlc.web.druid.datasource.filters}")
    private String filters;

    /**
     * The Schema.
     */
    @Value("${dlc.web.druid.datasource.schema}")
    private String schema;

    /**
     * Druid data source data source.
     *
     * @return the data source
     */
    @Bean(name = "druidDataSource")
    @Primary
    public DataSource druidDataSource(){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            LOGGER.error("druid configuration initialization filter", e);
        }
        return datasource;
    }

    /**
     * Init db initializing bean.
     *
     * @return the initializing bean
     */
    @Bean
    public InitializingBean initDb() {
        ClassPathResource sqlResource = new ClassPathResource(schema);
        DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(druidDataSource());
        dsi.setDatabasePopulator(new ResourceDatabasePopulator(true, true, "utf-8", sqlResource));
        dsi.setEnabled(true);
        return dsi;
    }
}

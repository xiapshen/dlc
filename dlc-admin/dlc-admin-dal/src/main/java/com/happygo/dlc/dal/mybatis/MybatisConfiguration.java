/**
 * Copyright  2017
 *
 * All  right  reserved.
 *
 * Created  on  2017年7月30日 下午7:40:48
 *
 * @Package com.happygo.dlc.dal.mybatis
 * @Title: MybatisConfiguration.java
 * @Description: MybatisConfiguration.java
 * @author sxp (1378127237@qq.com)
 * @version 1.0.0
 */
package com.happygo.dlc.dal.mybatis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * ClassName:MybatisConfiguration
 * @Description: MybatisConfiguration.java
 * @author sxp (1378127237@qq.com) 
 * @date:2017年7月30日 下午7:40:48
 */
@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DruidDataSourceConf.class)
@MapperScan(basePackages = "com.happygo.dlc.dal.mybatis.mapper")
public class MybatisConfiguration {
    /**
     * Logger the LOGGER
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * The Druid data source.
     */
    @Autowired
    @Qualifier("druidDataSource")
    private DataSource druidDataSource;

    /**
     * Session factory bean sql session factory bean.
     *
     * @return the sql session factory bean
     */
    @Bean
    public SqlSessionFactoryBean sessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置mybatis configuration 扫描路径
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/MyBatis-Configuration.xml"));
        sqlSessionFactoryBean.setDataSource(druidDataSource);
        try {
            Resource[] mapperLocations = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:mybatis/mapper/*.xml");
            sqlSessionFactoryBean.setMapperLocations(mapperLocations);
        } catch (IOException e) {
            LOGGER.error("Load mapper xml loactions error!", e);
        }
        return sqlSessionFactoryBean;
    }

    /**
     * Transaction manager platform transaction manager.
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource);
    }
}

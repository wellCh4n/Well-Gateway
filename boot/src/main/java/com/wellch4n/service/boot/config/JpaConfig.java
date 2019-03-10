package com.wellch4n.service.boot.config;

import com.wellch4n.service.env.EnvironmentContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


/**
 * @author wellCh4n
 * @description
 * @create 2019/03/10 14:04
 * 下周我就努力工作
 */

@Configuration
@ComponentScan(basePackages = {"com.wellch4n"})
@EnableJpaRepositories(basePackages = {"com.wellch4n.service.repository"},
        entityManagerFactoryRef = "entityManagerFactoryBean")
public class JpaConfig {

    @Autowired
    private EnvironmentContext environmentContext;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean () {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.wellch4n.service.domain");

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environmentContext.getJDBCDriverClassName());
        dataSource.setUrl(environmentContext.getJDBCUrl());
        dataSource.setUsername(environmentContext.getJDBCUsername());
        dataSource.setPassword(environmentContext.getJDBCPassword());
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}

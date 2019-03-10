package com.wellch4n.service.env;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 13:30
 * 下周我就努力工作
 */

@Component
@PropertySource(value = "file:${user.dir}/conf/application.properties")
public class EnvironmentContext {

    @Autowired
    private Environment environment;

    public String getJDBCDriverClassName() {
        return environment.getProperty("jdbc.driverClassName");
    }

    public String getJDBCUrl() {
        return environment.getProperty("jdbc.url");
    }

    public String getJDBCUsername() {
        return environment.getProperty("jdbc.username");
    }

    public String getJDBCPassword() {
        return environment.getProperty("jdbc.password");
    }

    public Integer getPort() {
        return Integer.parseInt(environment.getProperty("http.port", "9527"));
    }

    public Double getBloomFilterFPP() {
        return Double.parseDouble(environment.getProperty("bloomFilter.fpp", "0.01"));
    }

    public String getRedisHost() {
        return environment.getProperty("redis.host", "127.0.0.1");
    }

    public Integer getRedisPort() {
        return Integer.parseInt(environment.getProperty("redis.port", "6379"));
    }

    public String getRedisPassword() {
        return environment.getProperty("redis.password", "");
    }

    public Integer getRedisDbIndex() {
        return Integer.parseInt(environment.getProperty("redis.db", "6"));
    }

    public Long getRedisTimeout() {
        return Long.parseLong(environment.getProperty("redis.timeout", "10000"));
    }
}

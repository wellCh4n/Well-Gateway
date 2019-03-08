package com.wellch4n.service.boot.config;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.wellch4n.service.env.EnvironmentContext;
import com.wellch4n.service.impl.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 17:50
 * 下周我就努力工作
 */
@Configuration
@ComponentScan(basePackages = {"com.wellch4n"})
public class BloomFilterConfig {

    @Autowired
    private EnvironmentContext environmentContext;

    @Bean
    public BloomFilter<String> bloomFilter() {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), bloomFilterService().countBloomFilterField(), environmentContext.getBloomFilterFPP());

        for (int i = 0; i < 100; i++) {
            bloomFilter.put(""+ i);
        }

        return bloomFilter;
    }

    @Bean
    public BloomFilterService bloomFilterService() {
        return new BloomFilterService();
    }
}

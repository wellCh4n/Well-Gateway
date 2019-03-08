package com.wellch4n.service.impl;

import com.google.common.hash.BloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 16:11
 * 下周我就努力工作
 */

@Service
public class BloomFilterService {

    @Autowired
    private BloomFilter<String> bloomFilter;

    @SuppressWarnings("unused")
    public boolean mightContains(String path) {
        return bloomFilter.mightContain(path);
    }

    public int countBloomFilterField() {
        return 100;
    }
}

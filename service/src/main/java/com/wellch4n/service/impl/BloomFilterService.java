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

@SuppressWarnings({"unchecked", "ConstantConditions"})
@Service
public class BloomFilterService {

    @Autowired
    private BloomFilter<String> bloomFilter;

    public boolean mightContains(String path) {
        return bloomFilter.mightContain(path);
    }

    public void put(String path) {
        bloomFilter.put(path);
    }

    /**
     * 逻辑删除，在 mightContain 之前判断
     * @param path
     */
    public void remove(String path) {

    }
}

package com.wellch4n.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wellch4n.service.dto.ApiInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/13 14:42
 * 下周我就努力工作
 */

@SuppressWarnings("ConstantConditions")
@Service
public class CacheService {

    private static final String COUNT_KEY_PREFIX = "count#";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public long requestCount(String path) {
        long count = redisTemplate.opsForValue().increment(COUNT_KEY_PREFIX + path);
        if (count == 1) {
            redisTemplate.expire(COUNT_KEY_PREFIX + path, 1, TimeUnit.MINUTES);
        }
        return count;
    }

    public boolean isAllow(String path, long count) {
        // key时间内第一次访问
        if (!redisTemplate.hasKey(path)) {
            return true;
        }
        ApiInfoDTO apiInfoDTO = JSONObject.parseObject(redisTemplate.opsForValue().get(path), ApiInfoDTO.class);
        return count <= apiInfoDTO.getAllowCount();
    }

}

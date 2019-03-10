package com.wellch4n.web.verticles;


import com.google.common.base.Strings;
import com.wellch4n.service.dto.ApiInfoDTO;
import com.wellch4n.service.impl.ApiService;
import com.wellch4n.service.util.RequestUtil;
import com.wellch4n.service.util.ResponseUtil;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:19
 * 下周我就努力工作
 */

public class RequestVerticle {

    private ApplicationContext context;

    public RequestVerticle(ApplicationContext context) {
        this.context = context;
    }

    public void doRequest(RoutingContext routingContext) {
        ApiService apiService = context.getBean(ApiService.class);
        String path = RequestUtil.requestLastPath(routingContext);

        RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);

        String target = redisTemplate.opsForValue().get(path);
        if (!Strings.isNullOrEmpty(target)) {
            routingContext.response().end(target);
        } else {
            ApiInfoDTO apiInfoDTO = apiService.findByPath(path);
            if (Objects.isNull(apiInfoDTO)) {
                ResponseUtil.response404(routingContext);
            }
            redisTemplate.opsForValue().set(apiInfoDTO.getPath(), apiInfoDTO.getTarget());
            routingContext.response().end(apiInfoDTO.getTarget());
        }
    }
}

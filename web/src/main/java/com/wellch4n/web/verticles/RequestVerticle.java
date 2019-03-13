package com.wellch4n.web.verticles;


import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.wellch4n.service.dto.ApiInfoDTO;
import com.wellch4n.service.dto.RequestDTO;
import com.wellch4n.service.impl.ApiService;
import com.wellch4n.service.util.RequestUtil;
import io.netty.channel.ChannelFuture;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.UUID;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:19
 * 下周我就努力工作
 */

public class RequestVerticle extends BizVerticle{

    private ApplicationContext context;

    private ChannelFuture future;

    public RequestVerticle(ApplicationContext context, ChannelFuture future) {
        this.context = context;
        this.future = future;
    }

    public void doRequest(RoutingContext routingContext) {
        this.routingContext = routingContext;

        ApiService apiService = context.getBean(ApiService.class);
        String path = RequestUtil.requestLastPath(routingContext);

        RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);

        String target = redisTemplate.opsForValue().get(path);
        if (!Strings.isNullOrEmpty(target)) {

            response2xx(waitResponse(future, target));
        } else {
            ApiInfoDTO apiInfoDTO = apiService.findByPath(path);
            if (Objects.isNull(apiInfoDTO)) {
                response404();
            }
            redisTemplate.opsForValue().set(apiInfoDTO.getPath(), apiInfoDTO.getTarget());
            response2xx(target);
        }
    }

    private String waitResponse(ChannelFuture future, String target) {
        return waitResponse(future, target, null);
    }

    private String waitResponse(ChannelFuture future, String target, String params) {
        String uuid = UUID.randomUUID().toString();

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setUrl(target);
        requestDTO.setUuid(uuid);
        requestDTO.setParams(params);

        RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);

        future.channel().writeAndFlush(JSONObject.toJSONString(requestDTO));

        while (true) {
            String response = redisTemplate.opsForValue().get(uuid);
            if (!Strings.isNullOrEmpty(response)) {
                return response;
            }
        }
    }
}

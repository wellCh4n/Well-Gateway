package com.wellch4n.service.engine;

import com.alibaba.fastjson.JSONObject;
import com.wellch4n.service.dto.ApiInfoDTO;
import com.wellch4n.service.impl.ApiService;
import com.wellch4n.service.util.RequestUtil;
import com.wellch4n.service.util.ResponseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/16 22:54
 * 下周我就努力工作
 */

@SuppressWarnings("unchecked")
public class GatewayServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private ApplicationContext context;

    public GatewayServerHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        if (httpObject instanceof FullHttpRequest) {

            FullHttpRequest fullHttpRequest = (FullHttpRequest) httpObject;
            String path = RequestUtil.requestPath(fullHttpRequest);

            ApiService apiService = context.getBean(ApiService.class);

            RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);

            String apiInfoDTOString = redisTemplate.opsForValue().get(path);
            ApiInfoDTO apiInfoDTO = JSONObject.parseObject(apiInfoDTOString, ApiInfoDTO.class);

            try {
                // 缓存为空
                if (Objects.isNull(apiInfoDTO)) {
                    apiInfoDTO = apiService.findByPath(path);
                    // DB为空
                    if (Objects.isNull(apiInfoDTO)) {
                        ctx.writeAndFlush(ResponseUtil.build404Response());
                        return;
                    }
                    redisTemplate.opsForValue().set(apiInfoDTO.getPath(), JSONObject.toJSONString(apiInfoDTO));
                }
                String httpResponse = RequestUtil.doRequest(fullHttpRequest, apiInfoDTO.getTarget());
                ctx.writeAndFlush(ResponseUtil.build200Response(httpResponse));
            } catch (Exception e) {
                ctx.writeAndFlush(ResponseUtil.build403Response(e.getMessage()));
            }
        }
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.writeAndFlush(ResponseUtil.build403Response(cause.getMessage()));
        ctx.close();
    }
}

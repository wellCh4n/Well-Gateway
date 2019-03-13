package com.wellch4n.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.wellch4n.service.dto.RequestDTO;
import com.wellch4n.service.env.EnvironmentContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/13 11:22
 * 下周我就努力工作
 */

@SuppressWarnings("unchecked")
@ChannelHandler.Sharable
public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    private ApplicationContext context;

    private EnvironmentContext environmentContext;

    public ServerRequestHandler(ApplicationContext context) {
        this.context = context;
        this.environmentContext = context.getBean(EnvironmentContext.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestDTO requestDTO = JSONObject.parseObject(msg.toString(), RequestDTO.class);
        String response = HttpClientUtil.get(HttpConfig.custom().url("http://" + requestDTO.getUrl()));
        RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set(requestDTO.getUuid(), response,
                environmentContext.getGatewayTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

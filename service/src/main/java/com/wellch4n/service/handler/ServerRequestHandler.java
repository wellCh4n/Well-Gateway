package com.wellch4n.service.handler;

import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.wellch4n.service.dto.RequestDTO;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/13 11:22
 * 下周我就努力工作
 */

@ChannelHandler.Sharable
public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    private ApplicationContext context;

    public ServerRequestHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestDTO requestDTO = JSONObject.parseObject(msg.toString(), RequestDTO.class);
        String response = HttpClientUtil.get(HttpConfig.custom().url("http://" + requestDTO.getUrl()));
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set(requestDTO.getUuid(), response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

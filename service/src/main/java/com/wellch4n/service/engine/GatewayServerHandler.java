package com.wellch4n.service.engine;

import com.alibaba.fastjson.JSONObject;
import com.wellch4n.service.dto.ApiInfoDTO;
import com.wellch4n.service.impl.ApiService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/16 22:54
 * 下周我就努力工作
 */

public class GatewayServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private ApplicationContext context;

    public GatewayServerHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        if (httpObject instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) httpObject;
            String path = httpRequest.uri().replaceFirst("/", "");

            ApiService apiService = context.getBean(ApiService.class);

            RedisTemplate<String, String> redisTemplate = context.getBean(RedisTemplate.class);

            String apiInfoDTOString = redisTemplate.opsForValue().get(path);
            ApiInfoDTO apiInfoDTO = JSONObject.parseObject(apiInfoDTOString, ApiInfoDTO.class);

            if (apiInfoDTO != null) {
                ByteBuf content = Unpooled.copiedBuffer("Ok", CharsetUtil.UTF_8);
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                channelHandlerContext.writeAndFlush(response);
            } else {
                apiInfoDTO = apiService.findByPath(path);
                if (Objects.isNull(apiInfoDTO)) {
                    ByteBuf content = Unpooled.copiedBuffer("No", CharsetUtil.UTF_8);
                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                    response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                    channelHandlerContext.writeAndFlush(response);
                }
                redisTemplate.opsForValue().set(apiInfoDTO.getPath(), JSONObject.toJSONString(apiInfoDTO));
                ByteBuf content = Unpooled.copiedBuffer("Ok", CharsetUtil.UTF_8);
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                channelHandlerContext.writeAndFlush(response);
            }
        }
    }
}

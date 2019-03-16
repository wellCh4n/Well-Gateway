package com.wellch4n.service.engine;

import com.wellch4n.service.impl.BloomFilterService;
import com.wellch4n.service.impl.CacheService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/16 22:58
 * 下周我就努力工作
 */

public class GatewayFilterHandler extends ChannelInboundHandlerAdapter {

    private ApplicationContext context;

    public GatewayFilterHandler(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequest httpRequest = (HttpRequest) msg;
        String path = httpRequest.uri().replaceFirst("/", "");

        BloomFilterService bloomFilterService = context.getBean(BloomFilterService.class);
        boolean isPass = bloomFilterService.mightContains(path);

        if (!isPass) {
            ByteBuf content = Unpooled.copiedBuffer("No", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
            return;
        }

        CacheService cacheService = context.getBean(CacheService.class);
        long count = cacheService.requestCount(path);
        isPass = cacheService.isAllow(path, count);
        if (!isPass) {
            ByteBuf content = Unpooled.copiedBuffer("No", CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
            return;
        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("1");
    }
}

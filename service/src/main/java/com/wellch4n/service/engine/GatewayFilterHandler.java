package com.wellch4n.service.engine;

import com.wellch4n.service.impl.BloomFilterService;
import com.wellch4n.service.impl.CacheService;
import com.wellch4n.service.namespace.MessageNamespace;
import com.wellch4n.service.util.RequestUtil;
import com.wellch4n.service.util.ResponseUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
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
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        String path = RequestUtil.requestPath(fullHttpRequest);

        BloomFilterService bloomFilterService = context.getBean(BloomFilterService.class);
        boolean isPass = bloomFilterService.mightContains(path);

        if (!isPass) {
            ctx.writeAndFlush(ResponseUtil.build403Response(MessageNamespace.NOT_FOUND));
            return;
        }

        CacheService cacheService = context.getBean(CacheService.class);
        long count = cacheService.requestCount(path);
        isPass = cacheService.isAllow(path, count);
        if (!isPass) {
            ctx.writeAndFlush(ResponseUtil.build403Response(MessageNamespace.OVER_LIMIT));
            return;
        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.writeAndFlush(ResponseUtil.build403Response(cause.getMessage()));
        ctx.close();
    }
}

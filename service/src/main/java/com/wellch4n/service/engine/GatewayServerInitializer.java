package com.wellch4n.service.engine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/16 22:52
 * 下周我就努力工作
 */

public class GatewayServerInitializer extends ChannelInitializer<SocketChannel> {
    private ApplicationContext context;

    public GatewayServerInitializer(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast(new HttpServerCodec());
        channelPipeline.addLast(new HttpObjectAggregator(65536));
        channelPipeline.addLast(new GatewayFilterHandler(context));
        channelPipeline.addLast(new GatewayServerHandler(context));
    }
}

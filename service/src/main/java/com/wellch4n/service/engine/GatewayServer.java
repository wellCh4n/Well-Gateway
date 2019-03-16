package com.wellch4n.service.engine;

import com.wellch4n.service.env.EnvironmentContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.net.InetSocketAddress;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/13 11:13
 * 下周我就努力工作
 */

public class GatewayServer implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(GatewayServer.class);

    private ApplicationContext context;

    private EnvironmentContext environmentContext;

    public GatewayServer(ApplicationContext context) {
        this.context = context;
        this.environmentContext = context.getBean(EnvironmentContext.class);
    }

    @Override
    public void run() {
        logger.info("Gateway server start...");
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(environmentContext.getGatewayPort()))
                    .childHandler(new GatewayServerInitializer(context));
            ChannelFuture channelFuture = serverBootstrap.bind(environmentContext.getGatewayPort()).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

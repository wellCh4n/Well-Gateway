package com.wellch4n.web;

import com.wellch4n.service.env.EnvironmentContext;
import com.wellch4n.service.handler.ClientRequestHandler;
import com.wellch4n.web.verticles.CommonVerticle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 14:03
 * 下周我就努力工作
 */
public class WebService {

    private static Logger logger = LoggerFactory.getLogger(WebService.class);

    private ApplicationContext context;

    private EnvironmentContext environmentContext;

    private Vertx vertx;

    public WebService(ApplicationContext context) {
        this.context = context;
        this.environmentContext = context.getBean(EnvironmentContext.class);
    }

    public void initService() {
        logger.info("WebService loading...");

        logger.info("Gateway client start...");
        ChannelFuture future = clientInit();

        vertx = Vertx.vertx();
        CommonVerticle.setContext(context);
        CommonVerticle.setEnvironmentContext(environmentContext);
        CommonVerticle.setFuture(future);
        vertx.deployVerticle(CommonVerticle.class.getName());


        logger.info("WebService start on {} port!", environmentContext.getPort());
    }

    public ChannelFuture clientInit() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast("decoder", new StringDecoder());
                            channelPipeline.addLast("encoder", new StringEncoder());
                            channelPipeline.addLast(new ClientRequestHandler());
                        }
                    });
            return bootstrap.connect("0.0.0.0", 9526).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.spliterator();
        }
        return null;
    }

    public void release() {
        if (vertx != null) {
            vertx.close();
        }
    }
}

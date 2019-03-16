package com.wellch4n.web.verticles;

import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 15:28
 * 下周我就努力工作
 */
public class FilterVerticle extends BizVerticle {

    private ApplicationContext context;

    public FilterVerticle(ApplicationContext context) {
        this.context = context;
    }

    public void doRequest(RoutingContext routingContext) {
        next();
    }
}

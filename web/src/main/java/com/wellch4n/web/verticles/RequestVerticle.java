package com.wellch4n.web.verticles;


import com.wellch4n.service.env.EnvironmentContext;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;


/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:19
 * 下周我就努力工作
 */

@SuppressWarnings("unchecked")
public class RequestVerticle extends BizVerticle{

    private ApplicationContext context;

    private EnvironmentContext environmentContext;

    public RequestVerticle(ApplicationContext context) {
        this.context = context;
        this.environmentContext = context.getBean(EnvironmentContext.class);
    }

    public void doRequest(RoutingContext routingContext) {
        next();
    }
}

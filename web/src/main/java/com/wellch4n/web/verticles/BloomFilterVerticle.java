package com.wellch4n.web.verticles;

import com.wellch4n.service.impl.BloomFilterService;
import com.wellch4n.service.util.RequestUtil;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 15:28
 * 下周我就努力工作
 */
public class BloomFilterVerticle {

    private ApplicationContext context;

    private static final String CONTAINS_METHOD = "mightContains";

    public BloomFilterVerticle(ApplicationContext context) {
        this.context = context;
    }

    public void filter(RoutingContext routingContext) {
        try {
            String methodName = RequestUtil.requestLastPath(routingContext);

            BloomFilterService bloomFilterService = context.getBean(BloomFilterService.class);
            boolean isPass = bloomFilterService.mightContains(methodName);
            if (isPass) {
                routingContext.next();
            } else {
                routingContext.response().end("no");
            }
        } catch (Exception e) {
            routingContext.response().end("no");
        }

    }
}

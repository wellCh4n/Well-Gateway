package com.wellch4n.web.verticles;

import com.wellch4n.service.impl.BloomFilterService;
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

    private static final String API_IDENTIFY = "api";

    public BloomFilterVerticle(ApplicationContext context) {
        this.context = context;
    }

    public void filter(RoutingContext routingContext) {
        try {
            Class<?> clazz = Class.forName(BloomFilterService.class.getName());
            Object obj = context.getBean(BloomFilterService.class);
            Method method = clazz.getMethod(CONTAINS_METHOD, String.class);

            String[] paths = routingContext.request().path().split("/");
            String methodName = paths[paths.length - 1];
            String bizName = paths[paths.length - 2];

            if (paths.length < 2) {
                throw new Exception("URL Path 长度不正确");
            }

            if (!API_IDENTIFY.equalsIgnoreCase(bizName)) {
                throw new Exception("非 API");
            }

            boolean isPass = (boolean) method.invoke(obj, methodName);

            if (isPass) {
                routingContext.response().end("ok");
            } else {
                routingContext.response().end("no");
            }
        } catch (Exception e) {
            routingContext.response().end("no");
        }

    }
}

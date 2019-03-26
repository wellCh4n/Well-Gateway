package com.wellch4n.web.verticles;

import com.wellch4n.service.util.RequestUtil;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/26 15:15
 * 下周我就努力工作
 */

public class RequestVerticle extends BizVerticle {

    private static final String SERVICE_TEMPLATE = "%sService";

    private ApplicationContext context;

    public RequestVerticle(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void doRequest(RoutingContext routingContext) {
        try {
            this.routingContext = routingContext;

            String[] pathArray = RequestUtil.requestPathArray(routingContext);
            String serviceName = pathArray[pathArray.length - 2];
            String methodName = pathArray[pathArray.length - 1];

            String serviceClazzName = String.format(SERVICE_TEMPLATE, serviceName);
            Object serviceObj = context.getBean(serviceClazzName);
            Method method = serviceObj.getClass().getMethod(methodName);
            Object value = method.invoke(serviceObj);
            routingContext.response().end(value.toString());
        } catch (NoSuchMethodException e) {
                e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

package com.wellch4n.service.util;

import io.vertx.ext.web.RoutingContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:33
 * 下周我就努力工作
 */

public class RequestUtil {

    public static String[] requestPath (RoutingContext routingContext) {
        return routingContext.request().path().split("/");
    }

    public static String requestLastPath (RoutingContext routingContext) {
        String[] paths = requestPath(routingContext);
        if (paths.length < 1) {
           return "";
        }
        return paths[paths.length - 1];
    }
}

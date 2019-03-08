package com.wellch4n.service.util;

import com.google.common.base.Strings;
import io.vertx.ext.web.RoutingContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 21:08
 * 下周我就努力工作
 */
public class ResponseUtil {

    private static final Integer SUCCESS_CODE = 200;

    private static final Integer FAIL_CODE = 402;

    public static void response2xx(RoutingContext routingContext, String message) {
        routingContext.response()
                .putHeader("content-type", "application/json;charset=utf-8")
                .setStatusCode(SUCCESS_CODE)
                .end(message);
    }

    public static void response4xx(RoutingContext routingContext, String message) {
        routingContext.response()
                .putHeader("content-type", "application/json;charset=utf-8")
                .setStatusCode(FAIL_CODE)
                .end(message);
    }
}

package com.wellch4n.web.verticles;

import io.vertx.ext.web.RoutingContext;
/**
 * @author wellCh4n
 * @description
 * @create 2019/03/12 11:46
 * 下周我就努力工作
 */

public abstract class BizVerticle {

    private static final Integer SUCCESS_CODE = 200;

    private static final Integer FAIL_CODE = 402;

    private static final Integer NOT_FOUND_CODE = 404;

    public RoutingContext routingContext = null;

    abstract public void doRequest(RoutingContext routingContext);

    public void response2xx(String message) {
        routingContext.response()
                .putHeader("content-type", "application/json;charset=utf-8")
                .setStatusCode(SUCCESS_CODE)
                .end(message);
    }

    public void response4xx(String message) {
        routingContext.response()
                .putHeader("content-type", "application/json;charset=utf-8")
                .setStatusCode(FAIL_CODE)
                .end(message);
    }

    public void response404(String message) {
        routingContext.response()
                .putHeader("content-type", "application/json;charset=utf-8")
                .setStatusCode(NOT_FOUND_CODE)
                .end(message);
    }

    public void response404() {
        response404("资源不存在");
    }
}

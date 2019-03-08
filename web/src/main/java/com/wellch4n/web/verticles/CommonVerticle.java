package com.wellch4n.web.verticles;

import com.wellch4n.service.env.EnvironmentContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import org.springframework.context.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 14:09
 * 下周我就努力工作
 */
public class CommonVerticle extends AbstractVerticle {
    private static ApplicationContext context;

    private static EnvironmentContext environmentContext;

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route().handler(CorsHandler.create("*")
                .allowedHeaders(allowHearders())
                .allowedMethods(allowMethods()));

        router.route("/*").handler(new BloomFilterVerticle(context)::filter);

        vertx.createHttpServer(new HttpServerOptions().setCompressionSupported(true))
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", environmentContext.getPort()));
    }

    private Set<String> allowHearders() {
        Set<String> allowHeaders = new HashSet<String>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        return allowHeaders;
    }

    private Set<HttpMethod> allowMethods() {
        Set<HttpMethod> allowMethods = new HashSet<>();
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.PUT);
        allowMethods.add(HttpMethod.OPTIONS);
        return allowMethods;
    }

    public static void setContext(ApplicationContext context) {
        CommonVerticle.context = context;
    }

    public static void setEnvironmentContext(EnvironmentContext environmentContext) {
        CommonVerticle.environmentContext = environmentContext;
    }
}

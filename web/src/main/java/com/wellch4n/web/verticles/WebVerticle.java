package com.wellch4n.web.verticles;


import com.wellch4n.service.env.EnvironmentContext;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;

import java.io.*;


/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:19
 * 下周我就努力工作
 */

@SuppressWarnings("unchecked")
public class WebVerticle extends BizVerticle{

    private ApplicationContext context;

    private EnvironmentContext environmentContext;

    public WebVerticle(ApplicationContext context) {
        this.context = context;
        this.environmentContext = context.getBean(EnvironmentContext.class);
    }

    public void doRequest(RoutingContext routingContext) {
        this.routingContext = routingContext;
        try {
            FileReader fileReader = new FileReader("./dist/index.html");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder chunk = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                chunk.append(line);
            }
            routingContext.response().putHeader(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=utf-8");
            routingContext.response().end(chunk.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

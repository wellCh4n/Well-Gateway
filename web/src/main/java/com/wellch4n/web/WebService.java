package com.wellch4n.web;

import com.wellch4n.service.env.EnvironmentContext;
import com.wellch4n.web.verticles.CommonVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 14:03
 * 下周我就努力工作
 */
public class WebService {

    private static Logger logger = LoggerFactory.getLogger(WebService.class);

    private ApplicationContext context;

    private EnvironmentContext environmentContext;

    private Vertx vertx;

    public WebService(ApplicationContext context) {
        this.context = context;
        this.environmentContext = context.getBean(EnvironmentContext.class);
    }

    public void initService() {
        logger.info("WebService loading...");

        vertx = Vertx.vertx();
        CommonVerticle.setContext(context);
        CommonVerticle.setEnvironmentContext(environmentContext);
        vertx.deployVerticle(CommonVerticle.class.getName());

        logger.info("WebService start on {} port!", environmentContext.getPort());
    }

    public void release() {
        if (vertx != null) {
            vertx.close();
        }
    }
}

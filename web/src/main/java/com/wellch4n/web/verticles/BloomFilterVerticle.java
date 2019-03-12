package com.wellch4n.web.verticles;

import com.wellch4n.service.impl.BloomFilterService;
import com.wellch4n.service.util.RequestUtil;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 15:28
 * 下周我就努力工作
 */
public class BloomFilterVerticle extends BizVerticle {

    private ApplicationContext context;

    public BloomFilterVerticle(ApplicationContext context) {
        this.context = context;
    }

    public void doRequest(RoutingContext routingContext) {
        this.routingContext = routingContext;

        try {
            String methodName = RequestUtil.requestLastPath(routingContext);

            BloomFilterService bloomFilterService = context.getBean(BloomFilterService.class);
            boolean isPass = bloomFilterService.mightContains(methodName);
            if (isPass) {
                next();
            } else {
                response404();
            }
        } catch (Exception e) {
            response404();
        }

    }
}

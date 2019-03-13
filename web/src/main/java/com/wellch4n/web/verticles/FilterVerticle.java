package com.wellch4n.web.verticles;

import com.wellch4n.service.impl.BloomFilterService;
import com.wellch4n.service.impl.CacheService;
import com.wellch4n.service.util.RequestUtil;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/08 15:28
 * 下周我就努力工作
 */
public class FilterVerticle extends BizVerticle {

    private ApplicationContext context;

    public FilterVerticle(ApplicationContext context) {
        this.context = context;
    }

    public void doRequest(RoutingContext routingContext) {
        this.routingContext = routingContext;

        try {
            String methodName = RequestUtil.requestLastPath(routingContext);

            BloomFilterService bloomFilterService = context.getBean(BloomFilterService.class);
            boolean isPass = bloomFilterService.mightContains(methodName);
            if (!isPass) {
                response404();
                return;
            }

            CacheService cacheService = context.getBean(CacheService.class);
            long count = cacheService.requestCount(methodName);
            isPass = cacheService.isAllow(methodName, count);
            if (isPass) {
                next();
            } else {
                response4xx("超过次数");
            }
        } catch (Exception e) {
            response4xx("服务器异常");
        }

    }
}

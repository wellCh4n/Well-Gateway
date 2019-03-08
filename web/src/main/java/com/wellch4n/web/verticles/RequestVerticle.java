package com.wellch4n.web.verticles;


import com.wellch4n.service.dto.ApiInfoDTO;
import com.wellch4n.service.impl.ApiService;
import com.wellch4n.service.util.RequestUtil;
import com.wellch4n.service.util.ResponseUtil;
import io.vertx.ext.web.RoutingContext;
import org.springframework.context.ApplicationContext;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:19
 * 下周我就努力工作
 */

public class RequestVerticle {

    private ApplicationContext context;

    public RequestVerticle(ApplicationContext context) {
        this.context = context;
    }

    public void doRequest(RoutingContext routingContext) {
        ApiService apiService = context.getBean(ApiService.class);
        String path = RequestUtil.requestLastPath(routingContext);

        // 1.在缓存中查找
        //TODO 查一遍缓存

        // 2.在DB中查找
        ApiInfoDTO apiInfoDTO = apiService.findByPath(path);

        // 3.回种进缓存
        //TODO 没有则回种缓存
        ResponseUtil.response2xx(routingContext, apiInfoDTO.getTarget());
    }
}

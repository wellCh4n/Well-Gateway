package com.wellch4n.service.util;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.google.common.collect.Maps;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.Map;

/**
 * @author wellCh4n
 * @description
 * @create 2019/03/09 04:33
 * 下周我就努力工作
 */

public class RequestUtil {

    public static String requestPath (FullHttpRequest fullHttpRequest) {
        return fullHttpRequest.uri().split("\\?")[0].replaceFirst("/", "");
    }


    @SuppressWarnings("unchecked")
    public static String doRequest(FullHttpRequest fullHttpRequest, String target) throws Exception {
        if (!target.toUpperCase().startsWith("HTTP://") || !target.toUpperCase().startsWith("HTTPS://")) {
            target = "http://" + target;
        }
        if (fullHttpRequest.method() == HttpMethod.GET) {
            // get请求
            return doGet(target);
        } else if (fullHttpRequest.method() == HttpMethod.POST && fullHttpRequest.headers()
                .get(HttpHeaderNames.CONTENT_TYPE).contentEquals(HttpHeaderValues.APPLICATION_JSON)) {
            // body参数
            String requestBody = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
            return doPost(target, requestBody);
        } else if (fullHttpRequest.method() == HttpMethod.POST) {
            // form参数
            Map<String, Object> params = Maps.newHashMap();
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullHttpRequest);
            decoder.offer(fullHttpRequest);
            List<InterfaceHttpData> paramList = decoder.getBodyHttpDatas();
            for (InterfaceHttpData param: paramList) {
                Attribute attribute = (Attribute) param;
                params.put(attribute.getName(), attribute.getValue());
            }
            return doPost(target, params);
        } else {
            throw new Exception("暂不支持");
        }
    }

    private static String doGet(String target) throws HttpProcessException {
        HttpConfig httpConfig = HttpConfig.custom()
                .url(target);
        return HttpClientUtil.get(httpConfig);
    }

    private static String doPost(String target, String bodyParams) throws HttpProcessException {
        HttpConfig httpConfig = HttpConfig.custom()
                .url(target)
                .json(bodyParams);
        return HttpClientUtil.post(httpConfig);
    }

    private static String doPost(String target, Map<String, Object> formParams) throws HttpProcessException {
        HttpConfig httpConfig = HttpConfig.custom()
                .url(target)
                .map(formParams);
        return HttpClientUtil.post(httpConfig);
    }
}

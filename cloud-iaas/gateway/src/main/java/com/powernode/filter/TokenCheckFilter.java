package com.powernode.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.constant.AuthConstant;
import com.powernode.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 每一个后端服务中都要求用户必须登录才能访问，使用token标识身份
 * 过滤器就是验证用户是否携带了token
 */
@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //获取请求的路径
        String path = request.getURI().getPath();
        //如果请求的路径是登录处理的url或者swagger的api文档，则放行，无需验证
        if (AuthConstant.ALLOW_URLS.contains(path)) {
            return chain.filter(exchange);
        }

        //获取请求头中传递的token
        List<String> authorization = request.getHeaders().get(AuthConstant.AUTHORIZATION);
        //判断是否传递了authorization
        if (!CollectionUtils.isEmpty(authorization)) {
            String token = authorization.get(0);
            if (StringUtils.hasText(token)) {
                //将bearer 替换为空字符串
                //获取真正的token
                String realToken = token.replaceFirst(AuthConstant.BEARER, "");
                //不但token有值，而且redis也有token则传递token是有效的
                if (StringUtils.hasText(realToken) && redisTemplate.hasKey(AuthConstant.LOGIN_TOKEN_PREFIX + realToken)) {
                    //放行
                    return chain.filter(exchange);
                }
            }

        }
        //执行到此--没有携带token，或者token内容不正确，或者redis中的token过期了
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Result<Object> result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "没有正确的token或者已经过期");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

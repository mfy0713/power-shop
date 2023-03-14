package com.powernode.interceptor;

import com.powernode.constant.AuthConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class FeignInterceptor implements RequestInterceptor {
    @Override      //一旦执行远程调用，就执行此方法
    public void apply(RequestTemplate requestTemplate) {
        //获取当前请求的token和loginType
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (!ObjectUtils.isEmpty(servletRequestAttributes)) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String token = request.getHeader(AuthConstant.AUTHORIZATION);
            String loginType = request.getHeader(AuthConstant.LOGIN_TYPE);
            if (StringUtils.hasText(token) && StringUtils.hasText(loginType)) {
                //传递到下一个请求
                requestTemplate.header(AuthConstant.AUTHORIZATION, token);
                requestTemplate.header(AuthConstant.LOGIN_TYPE, loginType);
            }

        }
    }}

package com.powernode.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.constant.AuthConstant;
import com.powernode.constant.ResourceConstant;
import com.powernode.domain.LoginSysUser;
import com.powernode.model.Result;
import com.powernode.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
public class TokenTranslateFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        //将监控路径，或者文档路径排除，无需执行token转换
        if (PathUtil.pathIsMatch(ResourceConstant.RESOURCE_ALLOW_URLS, path)) {
            filterChain.doFilter(request, response);
            return;
        }
        //获取authorization
        String authorization = request.getHeader(AuthConstant.AUTHORIZATION);
        //获取登录类型
        String loginType = request.getHeader(AuthConstant.LOGIN_TYPE);
        if (StringUtils.hasText(authorization)) {
            //获取token
            String token = authorization.replaceFirst(AuthConstant.BEARER, "");
            if (StringUtils.hasText(token)) {
                //从redis中获取真是用户身份
                String authStr = redisTemplate.opsForValue().get(AuthConstant.LOGIN_TOKEN_PREFIX + token);

                if (StringUtils.hasText(authStr)) {
                    //token续约
                    token = renewToken(token);

                    //将身份字符串转换为security中的UsernameAndPasswordAuthentication
                    UsernamePasswordAuthenticationToken authenticationToken = JSON.parseObject(authStr, UsernamePasswordAuthenticationToken.class);
                    UsernamePasswordAuthenticationToken realToken = null;
                    //处理权限
                    switch (loginType) {
                        case AuthConstant.SYS_USER: {
                            LoginSysUser loginSysUser = JSON.parseObject(authenticationToken.getPrincipal().toString(), LoginSysUser.class);
                            //将用户的perms转换位权限
                            List<SimpleGrantedAuthority> authorityList = loginSysUser.getPerms()
                                    .stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());
                            //将权限赋给当前用户
                            realToken = new UsernamePasswordAuthenticationToken(loginSysUser, null, authorityList);
                        }
                        case AuthConstant.MEMBER: {

                        }
                        //讲用户身份对象保存到上下文
                        SecurityContextHolder.getContext().setAuthentication(realToken);
                        //继续执行过滤器
                        filterChain.doFilter(request, response);
                        return;
                    }
                }

            }
            //只要有一个条件不满足，则抛出错误返回
            Result<String> result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "token不存在或者过期");
            ObjectMapper objectMapper = new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(result);
            //设置响应类型
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(resultStr);
            writer.flush();
            writer.close();
        }
    }

    //token续约--redis中的token快过期（5分钟以内）用户访问时，自动将过期时间重新设置为2小时
    private String renewToken(String token) {
        //从redis中获取token的过期时间
        Long expired = redisTemplate.getExpire(AuthConstant.LOGIN_TOKEN_PREFIX + token, TimeUnit.SECONDS);
        //token存在，并且没有过期（-2），过期时间《=5分钟，则续约
        if (!ObjectUtils.isEmpty(expired) && !expired.equals(-2L) && expired <= AuthConstant.RENEW_EXPIRE_THRESHOLD) {
            //重新设置为2小时
            redisTemplate.expire(AuthConstant.LOGIN_TOKEN_PREFIX + token, Duration.ofSeconds(AuthConstant.TOKEN_EXPIRE_TIME));

        }
        return token;
    }
}

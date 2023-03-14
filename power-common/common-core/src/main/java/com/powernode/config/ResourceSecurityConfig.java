package com.powernode.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.constant.ResourceConstant;
import com.powernode.filter.TokenTranslateFilter;
import com.powernode.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)    //开启方法级别授权，使用注解@PreAuthorize("hasAuthority('')")
public class ResourceSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private TokenTranslateFilter tokenTranslateFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf
        http.csrf().disable();
        //执行身份认证的过滤之前，先执行token解析
        http.addFilterBefore(tokenTranslateFilter, UsernamePasswordAuthenticationFilter.class);
        //拒绝访问处理程序
        http.exceptionHandling().accessDeniedHandler(null);
        //资源授权
        http.authorizeRequests()
                .antMatchers(ResourceConstant.RESOURCE_ALLOW_URLS)
                .permitAll()
                .anyRequest()
                .authenticated();

    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return ((request, response, accessDeniedException) -> {
            System.out.println(accessDeniedException.getMessage());
            //只要有一个条件不满足，则抛出错误返回
            Result<String> result=Result.fail(HttpStatus.UNAUTHORIZED.value(), "权限不足，不能访问");
            ObjectMapper objectMapper = new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(result);
            //设置响应类型
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(resultStr);
            writer.flush();
            writer.close();
        });
    }
}

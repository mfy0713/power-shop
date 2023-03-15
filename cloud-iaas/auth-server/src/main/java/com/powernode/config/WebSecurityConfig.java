package com.powernode.config;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.constant.AuthConstant;
import com.powernode.model.LoginSuccess;
import com.powernode.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.PrintWriter;
import java.time.Duration;
import java.util.UUID;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;  //注入自定义的用户身份验证业务
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用自定义的业务类
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  //禁用csrf
        //设置自定义的登录url，登录成功之后的处理程序，失败的处理程序
        http.formLogin().loginProcessingUrl(AuthConstant.LOGIN_URL)
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler());
        //设置注销之后的处理逻辑
        http.logout().logoutUrl(AuthConstant.LOGOUT_URL)
                .logoutSuccessHandler(logoutSuccessHandler());
        //限制所有的资源都必须要登录才能访问
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean   //登录成功之后的处理程序
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return ((request, response, authentication) -> {
            //登录成功，创建token--uuid，保存redis，返回
            String token = UUID.randomUUID().toString();
            //将身份对象序列化位json字符串
            String authenticationStr = JSON.toJSONString(authentication);
            //保存redis，过期时间2小时
            redisTemplate.opsForValue().set(AuthConstant.LOGIN_TOKEN_PREFIX + token, authenticationStr, Duration.ofSeconds(AuthConstant.TOKEN_EXPIRE_TIME));
            //创建返回结果
            LoginSuccess loginSuccess = new LoginSuccess();
            loginSuccess.setType(AuthConstant.BEARER);
            loginSuccess.setExpiresIn(AuthConstant.TOKEN_EXPIRE_TIME.toString());
            loginSuccess.setAccessToken(token);
            //输出到流
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper objectMapper = new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(loginSuccess);
            PrintWriter writer = response.getWriter();
            writer.print(resultStr);
            writer.flush();
            writer.close();
        });
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return ((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            Result<String> result = new Result<>();
            result.setCode(HttpStatus.UNAUTHORIZED.value());
            if (exception instanceof LockedException){
                result.setMsg("账户被锁定");
            }
            else if(exception instanceof BadCredentialsException){
                result.setMsg("密码错误");
            }
            else if(exception instanceof DisabledException){
                result.setMsg("账户被禁用");
            }
            else if(exception instanceof AccountExpiredException){
                result.setMsg("账户过期");
            }
            else if(exception instanceof CredentialsExpiredException){
                result.setMsg("密码过期");
            }
            else
                result.setMsg("账户登录失败");
            //输出到流中
            ObjectMapper objectMapper=new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(result);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(resultStr);
            printWriter.flush();
            printWriter.close();

        });
    }

    @Bean   //注销
    public LogoutSuccessHandler logoutSuccessHandler(){
        return ((request, response, authentication) -> {
            // 获取用户输入的token
            String authorization = request.getHeader(AuthConstant.AUTHORIZATION);
            // 获取真实的token
            String token = authorization.replaceFirst(AuthConstant.BEARER,"");
            // 删除redis中的用户身份
            redisTemplate.delete(AuthConstant.LOGIN_TOKEN_PREFIX+token);
            Result<String> result = new Result<>();
            result.setMsg("注销成功");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ObjectMapper objectMapper = new ObjectMapper();
            String resultStr = objectMapper.writeValueAsString(result);
            PrintWriter writer = response.getWriter();
            writer.print(resultStr);
            writer.flush();
            writer.close();
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }
}

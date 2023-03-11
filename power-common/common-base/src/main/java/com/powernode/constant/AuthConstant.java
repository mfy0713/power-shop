package com.powernode.constant;

import java.util.Arrays;
import java.util.List;

public interface AuthConstant {
    /**
     * 放行的地址
     */
    List<String> ALLOW_URLS = Arrays.asList("/auth-server/doLogin");

    /**
     * token的请求头key
     */
    String AUTHORIZATION = "Authorization";

    /**
     * 请求token的前缀
     */
    String BEARER = "bearer ";

    /**
     * 登录的token前缀
     */
    String LOGIN_TOKEN_PREFIX = "login_token:";

    /**
     * 登录的地址
     */
    String LOGIN_URL = "/doLogin";

    /**
     * 登出的地址
     */
    String LOGOUT_URL = "/doLogout";

    /**
     * token的过期时间
     */
    Long TOKEN_EXPIRE_TIME = 7200L;

    String ACCESS_TOKEN = "access_token";

    String EXPIRES_IN = "expires_in";

    String TYPE = "type";

    /**
     * 监控的地址
     */
    String[] MONITOR_URLS = {"/actuator/**", "/druid/**"};

    /**
     * 登录类型
     */
    String LOGIN_TYPE = "loginType";

    String SYS_USER = "sysUser";

    String MEMBER = "member";

    /**
     * token过期的临界值
     */
    Long RENEW_EXPIRE_THRESHOLD = 300L;


}


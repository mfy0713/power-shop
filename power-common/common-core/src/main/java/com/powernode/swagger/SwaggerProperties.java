package com.powernode.swagger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "swagger3")
public class SwaggerProperties {
    //扫描controller，包名
    private String basePackage;
    /**
     * 作者的名称
     */
    private String name ;
    /**
     * 主页
     */
    private String url;
    /**
     * 邮箱
     */
    private String email ;
    /**
     * 标题
     */
    private String title ;
    /**
     * 描述
     */
    private String description ;
    /**
     * 服务的团队
     */
    private String termsOfServiceUrl;
    /**
     * 授权信息
     */
    private String license ;
    /**
     * 授权的url
     */
    private String licenseUrl ;

    /**
     * 版本
     */
    private String version;


}

package com.powernode.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;

@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@EnableOpenApi
public class SwaggerConfiguration {

    @Autowired
    private SwaggerProperties swaggerProperties;
    @Autowired
    private Environment environment;


    @Bean
    public Docket docket(){
        boolean flag=false;
        //获取当前服务的profile
        String[] profiles=environment.getActiveProfiles();
        for(String profile:profiles){
            if(profile.equals("dev")||profile.equals("test")){
                flag=true;
                break;
            }
        }
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))  //swagger生成文档是扫描的包
                .build();
    }

    @Bean
    public ApiInfo apiInfo(){
        //构建联系方式
        Contact contact=new Contact(swaggerProperties.getName(),swaggerProperties.getUrl(),swaggerProperties.getEmail());
        return new ApiInfo(swaggerProperties.getTitle(),
                swaggerProperties.getDescription(),
                swaggerProperties.getVersion(),
                swaggerProperties.getTermsOfServiceUrl(),
                contact,
                swaggerProperties.getLicense(),
                swaggerProperties.getLicenseUrl(),
                new HashSet<>());
    }
}

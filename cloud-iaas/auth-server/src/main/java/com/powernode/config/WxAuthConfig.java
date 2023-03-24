package com.powernode.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "wx.auth")
public class WxAuthConfig {
    private String appId;
    private String appSecret;
    private String wxTokenUrl;
}

package com.powernode.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "qiniu.my")
@RefreshScope
@Data
public class FileConfig {
    private String ak;
    private String sk;
    private String bucket;
    private String domainName;
}

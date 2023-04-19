package ru.botanica.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "integrations.user-service")
@Data
public class UserServiceIntegrationProperties {
    private String url;
    private Integer connectionTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;

}

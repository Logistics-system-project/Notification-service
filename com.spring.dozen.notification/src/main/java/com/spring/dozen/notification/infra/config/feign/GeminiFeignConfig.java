package com.spring.dozen.notification.infra.config.feign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.spring.dozen.notification")
public class GeminiFeignConfig {
    @Bean
    public RequestInterceptor apiKeyInterceptor(@Value("${gemini.api.key}") String apiKey) {
        return requestTemplate -> requestTemplate.header("x-goog-api-key", apiKey);
    }
}
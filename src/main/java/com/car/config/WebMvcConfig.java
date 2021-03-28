package com.car.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Slf4j
@Configuration
@EnableTransactionManagement
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    InboundInterceptor inboundInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("Adding Interceptors.. ");
        registry.addInterceptor(inboundInterceptor)
                .excludePathPatterns("/_health",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/v2/api-docs",
                        "/error",
                        "/webjars/**",
                        "/**/favicon.ico",
                        "/**/*.js",
                        "/**/*.css");
        log.info("Added Interceptors.. ");
    }
}

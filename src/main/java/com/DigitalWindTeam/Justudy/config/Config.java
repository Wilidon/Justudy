package com.DigitalWindTeam.Justudy.config;

import com.DigitalWindTeam.Justudy.interceptor.MinimalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer
{
    private final MinimalInterceptor minimalInterceptor;

    @Autowired
    public Config(MinimalInterceptor minimalInterceptor) {
        this.minimalInterceptor = minimalInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)  {
        registry.addInterceptor(minimalInterceptor);
    }
}
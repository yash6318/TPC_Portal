package com.dbms.project.config;

import com.dbms.project.interceptor.DefaultInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class WebMvcSecurityConfig extends WebMvcConfigurerAdapter {
    @Autowired
    DefaultInterceptor defaultInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor);
    }
}
package com.example.board.config;

import com.example.board.interceptor.YeaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class BoardConfig implements WebMvcConfigurer {

    @Autowired
    private YeaInterceptor yeaInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(yeaInterceptor);
    }
}

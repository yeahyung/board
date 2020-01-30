package com.example.board.config;

import com.example.board.interceptor.YeaInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// addInterceptor를 해줘야 interceptor가 실행됨
@Component
public class BoardConfig implements WebMvcConfigurer {

    @Autowired
    private YeaInterceptor yeaInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(yeaInterceptor);
    }
}

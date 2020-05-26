package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// 配置DispatchServlet
@Configuration
public class ViewConfig implements WebMvcConfigurer {


    // 增加映射关系
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/login/page").setViewName("login");
    }
}

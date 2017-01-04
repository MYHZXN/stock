package com.myh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**").addResourceLocations("file:F:/MyEclipseProject/Stock/pic/");
        //registry.addResourceHandler("/pic/**").addResourceLocations("file:C:/Stock/pic/");
        super.addResourceHandlers(registry);
    }
}

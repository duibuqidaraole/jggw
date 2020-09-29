package com.mt.government.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc 配置
 * @author g
 * @date 2019-12-11 10:44
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * 添加静态资源路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:/opt/jiguan/images/");
    }
}

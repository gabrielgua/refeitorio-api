package com.gabrielgua.refeitorio.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class PageConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        var resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true); // makes pageable first page = 1 and not 0
        resolvers.add(resolver);
    }
}
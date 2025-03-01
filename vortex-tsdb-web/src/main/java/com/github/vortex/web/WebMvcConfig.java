package com.github.vortex.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.github.paganini2008.devtools.net.Urls;

/**
 * 
 * @Description: WebMvcConfig
 * @Author: Fred Feng
 * @Date: 02/01/2025
 * @Version 1.0.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/META-INF/static/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(true).setUseTrailingSlashMatch(true);
    }

    @Bean
    public HandlerInterceptor basicHandlerInterceptor() {
        return new BasicHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicHandlerInterceptor()).addPathPatterns("/**");
    }

    public static class BasicHandlerInterceptor implements HandlerInterceptor {

        private static final String WEB_ATTRIBUTE_CONTEXT_PATH = "contextPath";

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                Object handler) throws Exception {
            HttpSession session = request.getSession();
            if (session.getAttribute(WEB_ATTRIBUTE_CONTEXT_PATH) == null) {
                session.setAttribute(WEB_ATTRIBUTE_CONTEXT_PATH, getContextPath(request));
            }
            return true;
        }

        private String getContextPath(HttpServletRequest request) {
            return Urls.toHostUrl(request.getRequestURL().toString()) + request.getContextPath();
        }

    }
}

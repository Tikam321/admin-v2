package com.tikam.simple_admin_v2.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilterConfig> authFilterConfigFilterRegistrationBean(AuthFilterConfig authFilterConfig) {
        FilterRegistrationBean<AuthFilterConfig> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(authFilterConfig);
        registrationBean.addUrlPatterns("/admin/*"); // intercept all requests
        registrationBean.setOrder(1); // high priority
        return registrationBean;

    }
}

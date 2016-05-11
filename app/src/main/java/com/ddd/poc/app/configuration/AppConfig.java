package com.ddd.poc.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class AppConfig {

    @Bean
    public HttpMessageConverter jacksonHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}

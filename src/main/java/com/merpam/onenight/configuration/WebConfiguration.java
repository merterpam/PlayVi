package com.merpam.onenight.configuration;

import com.merpam.onenight.session.SecurityConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://dbilgili.github.io/", "https://dbilgili.github.io", "https://github.io", "https://github.io/");

        registry.addMapping("/party")
                .allowCredentials(true)
                .exposedHeaders(SecurityConstants.TOKEN_HEADER);
    }
}
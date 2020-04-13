package com.merpam.onenight;

import com.merpam.onenight.session.SecurityConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class OneNightApplication {

    private static final String[] CORS_ORIGINS = {"http://localhost:3000",
            "https://one-night-spotify.herokuapp.com",
            "https://playvi.herokuapp.com",
            "https://dbilgili.github.io",
            "https://playvi.co"};

    private static final boolean ALLOW_CREDENTIALS = true;

    private static final String[] EXPOSED_HEADERS = {SecurityConstants.TOKEN_AUTHORIZATION_HEADER};

    public static void main(String[] args) {
        SpringApplication.run(OneNightApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(CORS_ORIGINS)
                        .allowCredentials(ALLOW_CREDENTIALS)
                        .exposedHeaders(EXPOSED_HEADERS);
            }
        };
    }
}

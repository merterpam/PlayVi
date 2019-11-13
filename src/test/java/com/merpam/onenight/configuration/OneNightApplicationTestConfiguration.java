package com.merpam.onenight.configuration;

import com.merpam.onenight.spotify.service.SpotifyAuthWebService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class OneNightApplicationTestConfiguration {

    @Bean
    @Primary
    public SpotifyAuthWebService applicationService() {
        return mock(SpotifyAuthWebService.class);
    }
}

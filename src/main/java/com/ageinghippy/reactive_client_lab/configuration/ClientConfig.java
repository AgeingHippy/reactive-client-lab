package com.ageinghippy.reactive_client_lab.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Configuration
public class ClientConfig {

    @Value("${unsplash.search.uri}")
    private URI unsplashSearchUri;

    @Value("${unsplash.api.client-id}")
    private String unsplashSecret;

    @Value("${pexels.search.uri}")
    private URI pexelsUri;

    @Value("${pexels.api-key}")
    private String pexelsApiKey;

    @Bean
    public WebClient unsplashWebClient() {
        return WebClient.builder()
                .baseUrl(unsplashSearchUri.toString())
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, unsplashSecret);
                    httpHeaders.set("Accept-Version","v1");
                })
                .build();
    }

    @Bean
    public WebClient pexelsWebClient() {
        return WebClient.builder()
                .baseUrl(pexelsUri.toString())
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set(HttpHeaders.AUTHORIZATION, pexelsApiKey);
                })
                .build();
    }
}


package com.microservices.match.config;

import feign.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

@Configuration
public class TournamentFeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public OAuth2AccessTokenInterceptor tournamentOAuth2AccessTokenInterceptor( OAuth2AuthorizedClientManager authorizedClientManager) {
        return new OAuth2AccessTokenInterceptor("tournament", authorizedClientManager);
    }

//    @Bean
//    public OAuth2AuthorizedClientManager tournamentOAuth2AuthorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientService oAuth2AuthorizedClientService) {
//
//        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
//                .clientCredentials()
//                .build();
////        DefaultOAuth2AuthorizedClientManager
//        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager (
//                clientRegistrationRepository, oAuth2AuthorizedClientService);
//
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//        return authorizedClientManager;
//    }


//    @Bean
//    public RequestInterceptor userTokenFeignRequestInterceptor() {
//        return requestTemplate -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication instanceof JwtAuthenticationToken) {
//                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
//                requestTemplate.header("Authorization", "Bearer " + jwt.getTokenValue());
//            }
//        };
//    }

}

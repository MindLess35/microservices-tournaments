//package com.microservices.user.config;
//
//import feign.Logger;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//
//@Configuration
//public class TeamFeignConfig {
//
//    @Bean
//    Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL;
//    }
//
//    @Bean
//    public OAuth2AccessTokenInterceptor affOAuth2AccessTokenInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
//        return new OAuth2AccessTokenInterceptor("team", authorizedClientManager);
//    }
//
//    @Bean
//    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientRepository authorizedClientRepository) {
//
//        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
//                .clientCredentials()
//                .build();
//
//        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
//                clientRegistrationRepository, authorizedClientRepository);
//
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//        return authorizedClientManager;
//    }
//
//
////    @Bean
////    public RequestInterceptor userTokenFeignRequestInterceptor() {
////        return requestTemplate -> {
////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////            if (authentication instanceof JwtAuthenticationToken) {
////                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
////                requestTemplate.header("Authorization", "Bearer " + jwt.getTokenValue());
////            }
////        };
////    }
//
//}

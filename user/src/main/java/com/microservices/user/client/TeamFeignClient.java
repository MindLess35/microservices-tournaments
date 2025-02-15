package com.microservices.user.client;

import com.microservices.user.config.TeamFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@Bean
//public OAuth2AccessTokenInterceptor affOAuth2AccessTokenInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
//    return new OAuth2AccessTokenInterceptor("account-manager-client",
//            authorizedClientManager);
//}
//
//@FeignClient(
//        name = "account-manager-client",
//        configuration = AccountManagerFeignConfig.class
//)
//@FeignClient(name = "team", configuration = TeamFeignConfig.class)
//public interface TeamFeignClient {
//
//    @GetMapping("/api/v1/teams/{id}")
//    ResponseEntity<Void> getTeam(@PathVariable("id") Long id);
//}

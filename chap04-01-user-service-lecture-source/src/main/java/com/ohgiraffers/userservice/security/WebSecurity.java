package com.ohgiraffers.userservice.security;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableDiscoveryClient
public class WebSecurity {

    /* 설명. 인가(authorization)용 메소드 ,403*/
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        /* 설명. JWT 로그인 처리를 할 것이므로 CSRF는 신경쓸 필요 없다. */
        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((auth)-> auth
                .requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
        );

        return http.build();
    }

    /* 설명. 인증(authentication)용 메소드 ,401*/

}

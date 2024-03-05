package com.ohgiraffers.gateway.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.Set;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {

    }

    /* 설명. 토큰을 Authorization 키 값으로 가지고 오는 반별, 그 토큰이 유요한즈
    *    */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            /* 설명. ServerHttpRequest는 HttpServeletRequest와 달리 스프링의 WebFlux 기술을 활용(비둘기 통신)하기 위한 request */
            ServerHttpRequest request = exchange.getRequest();

            /* 설명. 토큰 들고 오는지 확인(RequestHeader에 "Authorization"이라는 키 값이 넘어오느냐) */
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Authorization 헤더 부재", HttpStatus.UNAUTHORIZED);
            }

            HttpHeaders headers =request.getHeaders();
            Set<String> keys = headers.keySet();
            Iterator<String> iter = keys.iterator();
            while (iter.hasNext()) {
                log.info(iter.next());
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if(!isJwtValid(jwt)) {
                return onError(exchange, "토큰이 유효하지 않음", HttpStatus.UNAUTHORIZED);
            }

            return  chain.filter(exchange);
        };
    }

    /* 설명. 유효한 토큰인지 확인 후 true or false를 반환*/
    /* 필기. userService에 있는 token 값과 dependencies를 가져와 적용시켜줘야 게이트웨이에서 유효한 토큰인지 판별할 수 있다. */
    private boolean isJwtValid(String jwt) {
        boolean flag = true;

        String subject = null;

        try {
        subject = Jwts
                .parser()
                .setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(jwt).getBody()
                .getSubject();
        }catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        if(subject == null || subject.isEmpty()) {
            flag = false;
        }
        return flag;
    }

    /* 설명. 에러 발생 시 401번 응답 코드로 반환*/
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }
}

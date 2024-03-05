package com.ohgiraffers.userservice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class Chap0401UserServiceLectureSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chap0401UserServiceLectureSourceApplication.class, args);
    }

    /* 설명. ModelMapper Bean으로 등록(필요하면 의존성 주입 받을 예정) */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();           // 현재는 STANDARD 모드이다.(임의적인 매핑도 가능한 상태이니 주의)
    }
}

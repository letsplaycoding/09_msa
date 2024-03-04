package com.ohgiraffers.secondservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/second-service")
public class SecondServiceController {
    @GetMapping("/health_check")
    public String healthCheck() {
        return "난 안괜차나";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String header) {
        return "Second Service Message";
    }
}

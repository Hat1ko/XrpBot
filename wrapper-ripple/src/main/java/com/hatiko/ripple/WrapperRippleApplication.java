package com.hatiko.ripple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@EnableScheduling
@SpringBootApplication
public class WrapperRippleApplication {
    public static void main(String[] args) {
    	ApiContextInitializer.init();
        SpringApplication.run(WrapperRippleApplication.class, args);
    }
}

package io.cryptex.ms.wrapper.ripple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WrapperRippleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WrapperRippleApplication.class, args);
    }
}

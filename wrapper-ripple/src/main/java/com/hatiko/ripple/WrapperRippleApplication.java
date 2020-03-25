package com.hatiko.ripple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

//@EnableScheduling
@SpringBootApplication
public class WrapperRippleApplication {
    public static void main(String[] args) {
    	
    	//init Telegram bot
    	ApiContextInitializer.init();
    	
    	//spring framework start
        SpringApplication.run(WrapperRippleApplication.class, args);
    }
}

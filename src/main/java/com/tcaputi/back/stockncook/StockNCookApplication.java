package com.tcaputi.back.stockncook;

import com.tcaputi.back.stockncook.common.config.GracefulTaskScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class StockNCookApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockNCookApplication.class, args);
    }

    @Bean
    @Primary
    public ThreadPoolTaskScheduler taskScheduler() {
        return new GracefulTaskScheduler();
    }

}

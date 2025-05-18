package com.tcaputi.back.stockncook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class StockNCookApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockNCookApplication.class, args);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setAwaitTerminationSeconds(1800);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}
